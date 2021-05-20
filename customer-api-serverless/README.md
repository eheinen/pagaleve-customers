# Serverless Customer API

Just configure your AWS keys with command:

```
serverless config credentials --provider aws --key KEY_HERE --secret SECRET_HERE -o 
```

`-o` will Override your default keys configured, be careful!

After that, just execute:

```
serverless deploy
```

The command above will wrappe the application and save the cloudformation yaml file on S3 bucket. then ity will create:
1 - DynamoDB Table
2 - API Gateway with all resources about Customers
3 - Lambda Functions (REST API)

### Important

It is not fully working, there is an Index.ts problem that is blocking to request the lambda function correctly. 
Beside, the whole environment will be created in few minutes.
