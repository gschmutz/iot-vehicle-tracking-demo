import faust

kafka_brokers = ['dataplatform:29092']

# convenience func for launching the app
def main() -> None:
    app.main()

app = faust.App('vehicle-tracking-app', broker=kafka_brokers)

# VehiclePosition Schema
class VehiclePosition(faust.Record, validation=True, serializer='json'):
    TIMESTAMP: str
    VEHICLEID: str
    DRIVERID: int
    ROUTEID: int
    EVENTTYPE: str
    LATITUDE: float
    LONGITUDE: float


rawVehiclePositionTopic = app.topic('vehicle_tracking_refined_json', value_type= VehiclePosition)
problematicDrivingTopic = app.topic('problematic_driving_faust', value_type= VehiclePosition)


@app.agent(rawVehiclePositionTopic)
async def process(positions):
    async for position in positions:
        print(f'Position for {position.VEHICLEID}')
        
        if position.EVENTTYPE != 'Normal': 
            await problematicDrivingTopic.send(value=position)  