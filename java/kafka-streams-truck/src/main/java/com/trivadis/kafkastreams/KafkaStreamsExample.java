package com.trivadis.kafkastreams;
import java.util.Properties;

import com.trivadis.avro.VehicleTrackingRefined;
import org.apache.commons.cli.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class KafkaStreamsExample {

	static final String VEHICLE_TRACKING_REFINED_STREAM = "vehicle_tracking_refined";
	static final String PROBLEMATIC_DRIVING_STREAM = "problematic_driving_kstreams";

	public static void main(final String[] args) {
		String applicationId = "test";
		String clientId = "test";
		String bootstrapServer = "localhost:9092";
		String schemaRegistryUrl = "http://localhost:8081";
		boolean cleanup = false;

		// create the command line parser
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption( "ai", "application-id", true, "REQUIRED: The application id." );
		options.addOption( "ci", "client-id", true, "The client id. Defaults to the application id + the prefix '-client'" );
		options.addOption( "b", "bootstrap-server", true, "The server(s) to connect to, default to " + bootstrapServer);
		options.addOption( "sr", "schemaRegistryUrl", true, "The schema registry to connect to for the Avro schemas, defaults to " + schemaRegistryUrl );
		options.addOption( "cl", "cleanup", false, "Should a cleanup be performed before staring. Defaults to false" );

		try {
			// parse the command line arguments
			CommandLine line = parser.parse( options, args );

			if( line.hasOption( "application-id" ) ) {
				applicationId = line.getOptionValue("application-id");
			}
			if( line.hasOption( "client-id" ) ) {
				clientId = line.getOptionValue("client-id");
			} else {
				clientId = applicationId + "-client";
			}

			if( line.hasOption( "bootstrap-server" ) ) {
				bootstrapServer = line.getOptionValue("bootstrap-server");
			}
			if( line.hasOption( "schemaRegistryUrl" ) ) {
				schemaRegistryUrl = line.getOptionValue("schemaRegistryUrl");
			}
			if( line.hasOption( "cleanup" ) ) {
				cleanup = true;
			}
		}
		catch( ParseException exp ) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "kafka-passthrough", exp.getMessage(), options,null, true);
		}
		String stateDirPath = "C:\\tmp\\kafka-streams";
		final KafkaStreams streams = buildFeed(applicationId, clientId, bootstrapServer, schemaRegistryUrl, stateDirPath);

		if (cleanup) {
			streams.cleanUp();
		}
		streams.start();

		// Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				streams.close();
			}
		}));
	}

	private static KafkaStreams buildFeed(final String applicationId, final String clientId, final String bootstrapServers, final String schemaRegistryUrl,
										  final String stateDir) {

		final Properties streamsConfiguration = new Properties();

		// Give the Streams application a unique name. The name must be unique in the
		// Kafka cluster
		// against which the application is run.
		streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
		streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, clientId);

		// Where to find Kafka broker(s).
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

		// Where to find the Confluent schema registry instance(s)
		streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

		// Specify default (de)serializers for record keys and for record values.
		streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
		streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
		streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// Records should be flushed every 10 seconds. This is less than the default
		// in order to keep this example interactive.
		streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);

		// If Confluent monitoring interceptors are on the classpath,
		// then the producer and consumer interceptors are added to the
		// streams application.
		// MonitoringInterceptorUtils.maybeConfigureInterceptorsStreams(streamsConfiguration);


		final StreamsBuilder builder = new StreamsBuilder();

		// read the source stream (keyed by objectId)
		final KStream<String, VehicleTrackingRefined> vehicleTracking = builder.stream(VEHICLE_TRACKING_REFINED_STREAM);


		// read the driver
		//final KTable<String, Driver> driver = builder.table(DRIVER_STREAM);

		vehicleTracking.peek((k,v) -> System.out.println("vehicleTracking.peek(...) : " + k + " : " + v));

		// Left Join Positions Mecomo Raw with Barge to get the barge id
		//KStream<String, PositionMecomo> positionsMecomo  =  positionsMecomoRaw.leftJoin(barge,
		//		(leftValue, rightValue) -> createFrom(leftValue, (rightValue != null ? rightValue.getId() : -1) ),
		//		Joined.<String, PositionMecomoRaw, Barge>keySerde(Serdes.String())
		//);

		final KStream<String, VehicleTrackingRefined> vehicleTrackingFiltered = vehicleTracking.filter((k,v) -> v.getEVENTTYPE() != "Normal");

		// Send the Matches to the Kafka Topic
		vehicleTrackingFiltered.to(PROBLEMATIC_DRIVING_STREAM);

		return new KafkaStreams(builder.build(), streamsConfiguration);
	}


}
