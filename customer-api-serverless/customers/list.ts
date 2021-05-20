'use strict'

import {DynamoDB} from 'aws-sdk'

const dynamoDb = new DynamoDB.DocumentClient()

module.exports.list = (event, context, callback) => {
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
    };
    
    dynamoDb.scan(params, (error, result) => {
        if (error) {
            console.error(error);
            callback(null, {
                statusCode: error.statusCode || 501,
                headers: {'Content-Type': 'text/plain'},
                body: 'Couldn\'t fetch customers.',
            });
            return;
        }

        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Items),
        };
        callback(null, response);
    });
};