## Decathlon Backend Web Application Developer Interview
## Problem Statement: Alert Mechanism System
Build a Rest API application to manage the alert system mechanism where we need to alert a
team and certain developers for alerts. Below are the endpoints to have:
1. POST /team - create team which has (team_id, team_name) and list of developers
   linked to a team which has(developer_id,name,phone_number, team_id).
   Consumers should be responded back with auto generated team_id after creation.
   Make sure that we cannot create a team without any developers. At least one developer
   should be mandatory.
   Request Payload for the endpoint:
   {
   "developers": [
   {
   "name": "string",
   "phoneNumber": "string"
   }
   ],
   "teamName": "string"
   }
2. POST /{team_id}/alert - take team_id in request and alert any team developer from the
   team and it has to be random for every alert. Use developer phone_numner to alert
   using below details.
   Use the SMS alert URL: https://run.mocky.io/v3/fd99c100-f88a-4d70-aaf7-393dbbd5d99f
   Request payload: {“phone_number”: “xxxxxxxxxx”}
   Points to consider:
- Application should be built in Springboot framework with maven/gradle build tool.
- Java version has to be equal or above 8.
- Exceptions have to be handled for forssen scenarios.
- Request validation to be done.
- Altest 2 unit test cases are mandatory.
- Local database should be available to save team and developer details.

###This application is developed based on the above requirement
#Developer details:
##Krishnanand Naik
##naikrish94@gmail.com
