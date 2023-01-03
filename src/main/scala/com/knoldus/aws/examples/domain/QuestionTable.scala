package com.knoldus.aws.examples.domain

import com.knoldus.dynamodb.dao.DynamoTable

case class QuestionTable(tableName: String) extends DynamoTable
