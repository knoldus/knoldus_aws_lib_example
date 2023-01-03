package com.knoldus.aws.examples.models

import com.knoldus.dynamodb.dao.DynamoTable

case class QuestionTable(tableName: String) extends DynamoTable
