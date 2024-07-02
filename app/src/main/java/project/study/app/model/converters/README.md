# Converters Package

## Overview

The converters package contains classes responsible for converting data between different formats. These converters are particularly useful for transforming data types to and from formats that are suitable for storage or transmission.

## Files

### QuestionListConverter.java

**Description:**  
A converter class that handles the conversion of a list of `Question` objects to a JSON string and vice versa. This is typically used for storing and retrieving lists of questions in a database or other storage medium.

**Key Responsibilities:**
- Converts a list of `Question` objects to a JSON string.
- Converts a JSON string back to a list of `Question` objects.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere

---

### QuestionTypeAdapter.java

**Description:**  
A type adapter for Gson that handles the serialization and deserialization of `Question` objects. This adapter ensures that `Question` objects are correctly converted to and from JSON format, preserving their type and structure.

**Key Responsibilities:**
- Serializes `Question` objects to JSON format.
- Deserializes JSON strings back into `Question` objects.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere