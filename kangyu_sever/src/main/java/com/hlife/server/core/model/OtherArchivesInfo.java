package com.hlife.server.core.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "otherArchivesInfo")
public abstract class OtherArchivesInfo<T> extends SubArchivesInfoOut<T> {
}
