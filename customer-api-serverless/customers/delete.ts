'use strict'

import {DynamoDB} from 'aws-sdk'

const dynamoDb = new DynamoDB.DocumentClient()

module.exports.get = (event, context, callback) => {
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            cpf: event.pathParameters.cpf,
        }
    }

    dynamoDb.delete(params, (error) => {
        if (error) {
            console.error(error);
            callback(null, {
                statusCode: error.statusCode || 501,
                headers: {'Content-Type': 'text/plain'},
                body: 'Couldn\'t delete customer.',
            });
            return;
        }

        const response = {
            statusCode: 200,
            body: "{}",
        };
        callback(null, response);
    })
}