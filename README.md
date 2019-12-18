# demo_01

## Task
### Requirements
1. Request & response in JSON format.
2. Request, response and date/time should be logged to separate file.
3. The project must be assembled in war, for installation on Tomcat.
4. If the request contains the "id" field with the value = 1, then the answer should be as in the example below, otherwise the answer should be NULL.
5. The format of the request is POST.
6. Implement encryption and decryption of AES-256 of the incoming request and response. This part needs only to be secured. For example (part of the log):

```=== encryption: sfdjnva9sfv87say9hdfow3```

```=== decryption: {"fio": "Test Testov"}```

Example BODY request:

```{"id": 1}```

BODY answer example:

```{"fio": "Test Testov"}```

## To test API via curl use 

### Create request

Instead of %VALUE% and %ADDRESS% insert real values

`curl -H "Accept: application/json" -H "Content-type: application/json" \
-X POST -d '{"id":"%VALUE%"}' %ADDRESS%`

Example 

`curl -H "Accept: application/json" -H "Content-type: application/json" \
-X POST -d '{"id":1}' http://localhost:8080/`
