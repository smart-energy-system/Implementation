echo "Test Script"
#Creates a battery
curl -X POST "http://localhost:8090/supplier/batteries" -H  "accept: */*" -H  "Content-Type: application/json" -d "
{
\"maximumChargingRate\": 4000, 
\"maximumDischargingRate\": 2000, 
\"maximumStoredEnergy\": 12000, 
\"storedEnergy\": 0}
" 
sleep 3s
#Show battery
curl -X GET "http://localhost:8090/supplier/batteries" -H  "accept: */*"
sleep 3s
#Create a solar panle
curl -X POST "http://localhost:8090/supplier/photovoltaicPanels" -H  "accept: */*" -H  "Content-Type: application/json" -d "
{
\"latitude\": 31.230391,
\"longitude\": 121.473701,
\"maximumPowerYield\": 260,
\"moduleArea\": 10,
\"tiltAngle\": 0.3}
"
sleep 3s
#Show solar panel
curl -X GET "http://localhost:8090/supplier/photovoltaicPanels" -H  "accept: */*"
sleep 3s
#Create wind turbine (use bladeRadius 80 if no wind, default 40)
curl -X POST "http://localhost:8090/supplier/windTurbines" -H  "accept: */*" -H  "Content-Type: application/json" -d "
{
\"bladeRadius\": 40,
\"efficiency\": 1,
\"latitude\": 31.230391,
\"longitude\": 121.473701}
"
sleep 3s
#Show wind turbine
curl -X GET "http://localhost:8090/supplier/windTurbines" -H  "accept: */*"
sleep 3s
#Create home consumer
curl -X POST "http://localhost:8090/consumer/homes" -H  "accept: */*" -H  "Content-Type: application/json" -d "
{ 
\"averageDailyOccupancy\": 1,
\"demandFlexibility\": 0.5,
\"floorAreaSize\": 1000}
"
sleep 3s
#Show consumer
curl -X GET "http://localhost:8090/consumer/homes" -H  "accept: */*"
sleep 3s
#Create office consumer

curl -X POST "http://localhost:8090/consumer/officeBuildings" -H  "accept: */*" -H  "Content-Type: application/json" -d "
{  
\"averageDailyOccupancy\": 1,
\"demandFlexibility\": 0.5,
\"floorAreaSize\": 100}
"
sleep 3s
#Show office building
curl -X GET "http://localhost:8090/consumer/officeBuildings" -H  "accept: */*"
sleep 5s
#Start solver
curl -X GET "http://localhost:8090/solver?calculationBound=100&efficiencyChargingAsPartsOfHundred=80&exportPrice=4" -H  "accept: */*"
sleep 3s