package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;

import com.oracle.saas.spectra.platform.client.*;
import com.oracle.saas.spectra.platform.client.api.SignalDefinitionsApi;
import com.oracle.saas.spectra.platform.client.model.CreateSignalDefinitionDetails;

import java.io.IOException;
import java.security.InvalidKeyException;

public class OSSPSinalDefinition {
    public static void main(String[] args) throws InvalidKeyException, ApiException, IOException {
        // Define a auth provider to use
        BasicAuthenticationDetailsProvider authProvider = new ConfigFileAuthenticationDetailsProvider(ConfigFileReader.parse("~/.oci/config", "DEFAULT"));
        // Start a ossp client
        OSSPClient osspClient = new OSSPClient(authProvider);
        // Get signal definition api
        SignalDefinitionsApi sigDefsApi = osspClient.getSignalDefsApi();

        String repoName = "fa.cx.marketing.common";
        String signalDefName = "example-signal-def";
        String signalVersion = "0.0.2";
        ObjectMapper mapper = new ObjectMapper();
        // provide the raw json-schema here
        JsonNode inputSchemaJson = mapper.readTree(getInputSchema());
        JsonNode artifactMetadata = mapper.readTree(getArtifactMetaData());
        CreateSignalDefinitionDetails details = CreateSignalDefinitionDetails
                .builder()
                .description("sample description")
                .inputSchema(inputSchemaJson)
                .defaultTimeoutSeconds(400)
                .published(true)
                .artifactMetadata(artifactMetadata)
                .build();

        // Create signal definition
        sigDefsApi.createSignalDefinition(repoName, signalDefName, signalVersion, details);

    }

    private static String getArtifactMetaData() {

        return "{\n" +
                "  \"artifactMetadata\": {\n" +
                "    \"gitRepo\": \"https://bitbucket.oci.oraclecorp.com/projects/my-project/\",\n" +
                "    \"slackChannel\": \"#my-slack-channel\"\n" +
                "  }\n" +
                "}";
    }

    private static String getInputSchema() {

        return "{\n" +
                "  \"inputSchema\": {\n" +
                "    \"properties\": {\n" +
                "      \"targetEndpoint\": {\n" +
                "        \"description\": \"The target endpoint the signal handler should invoke.\",\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      \"debug\": {\n" +
                "        \"description\": \"This flag enabled debug mode\",\n" +
                "        \"type\": \"boolean\",\n" +
                "        \"default\": false\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }
}
