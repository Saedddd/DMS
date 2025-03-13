1. To test the setup:
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/metrics
curl http://localhost:8080/actuator/loggers

2. Access Actuator endpoints:
curl http://localhost:8080/actuator/metrics/dms.documents.created

3. Check metrics for a specific counter:
curl http://localhost:8080/actuator/metrics/dms.documents.created

4. Trigger a 500 error and check logs:
tail -f logs/app.log