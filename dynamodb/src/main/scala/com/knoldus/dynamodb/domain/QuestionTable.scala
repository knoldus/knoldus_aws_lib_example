package com.knoldus.dynamodb.domain

import com.knoldus.dynamodb.dao.DynamoTable

case class QuestionTable(tableName: String) extends DynamoTable
