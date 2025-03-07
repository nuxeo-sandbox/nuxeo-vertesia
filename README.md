# Description
A plugin that provides a simple and easy to use integration pattern
between [Vertesia](https://vertesiahq.com/) and the Nuxeo Platform.

# How to build
```bash
git clone https://github.com/nuxeo-sandbox/nuxeo-vertesia
cd nuxeo-vertesia
mvn clean install -DskipTests
```

# Features
## Automation API
The integration between the Nuxeo Platform and Vertesia is meant to be as versatile as possible and leverages Nuxeo's automation framework.

### Run Execution
The operation to execute a Vertesia interaction is `Vertesia.ExecInteraction`

Parameters:

| Name             | Description                                                              | Type            | Required | Default value |
|:-----------------|:-------------------------------------------------------------------------|:----------------|:---------|:--------------|
| interactionId    | The interaction ID                                                       | string          | true     |               |
| environmentId    | The run environment ID                                                   | string          | true     |               |
| modelId          | The Model ID (required if the environment does not have a default model) | string          | false    |               |
| interactionInput | The interaction JSON input                                               | string          | true     |               |
| temperature      | The model temperature                                                    | string (double) | false    |               |
| max_tokens       | The maximum number of tokens for the output                              | string (long)   | false    |               |
| tags             | Tags to associate to the Vertesia run                                    | Array of string | false    |               |

Output: A string Blob containing the Vertesia REST API JSON response

### Example
Below is an automation script example which uses the text extract of a file document, uses it with an interaction and stores the result in `dc:description`:

```javascript
function run(input, params) {
  
  var blob = input['file:content'];
  
  var textBlob = Blob.RunConverter(blob, {
      'converter': 'any2text'
  });

  Console.log(textBlob.getString());
  
  // The interaction input object must contain the parameters defined in the prompt template
  var interactionInput = {
      text: textBlob.getString()
  };
  
  var ccResponse = Vertesia.ExecInteraction(null, {
      'interactionId': 'MyInteractionId', 
      'environmentId': 'MyEnvironmentId', 
      'modelId': 'MyModelId', 
      'interactionInput': JSON.stringify(interactionInput),
      'tags': ['tag1','tag2'],
      'temperature':'0.9',
      'max_tokens': '100'
  });
  
  Console.log(ccResponse.getString());
  
  var result = JSON.parse(ccResponse.getString()).result;
  
  // The result object structure corresponds to the output schema defined in the interaction
  Console.log(result);
  
  input['dc:description'] = result.translation;
  
  return Document.Save(input, {});

}
```

# How to run
## Configuration
The following nuxeo.conf properties must be configured in order to use the plugin

| Property name         | description                                           |
|-----------------------|-------------------------------------------------------|
| vertesia.api.url      | The API base url                                      |
| vertesia.api.key      | The API key to use when calling the Vertesia REST API |
| vertesia.http.timeout | The timeout for http requests (default 60 seconds)    |


# Support
**These features are not part of the Nuxeo Production platform.**

These solutions are provided for inspiration and we encourage customers to use them as code samples and learning
resources.

This is a moving project (no API maintenance, no deprecation process, etc.) If any of these solutions are found to be
useful for the Nuxeo Platform in general, they will be integrated directly into platform, not maintained here.

# License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

# About Nuxeo
Nuxeo Platform is an open source Content Services platform, written in Java. Data can be stored in both SQL & NoSQL
databases.

The development of the Nuxeo Platform is mostly done by Nuxeo employees with an open development model.

The source code, documentation, roadmap, issue tracker, testing, benchmarks are all public.

Typically, Nuxeo users build different types of information management solutions
for [document management](https://www.nuxeo.com/solutions/document-management/), [case management](https://www.nuxeo.com/solutions/case-management/),
and [digital asset management](https://www.nuxeo.com/solutions/dam-digital-asset-management/), use cases. It uses
schema-flexible metadata & content models that allows content to be repurposed to fulfill future use cases.

More information is available at [www.nuxeo.com](https://www.nuxeo.com).
