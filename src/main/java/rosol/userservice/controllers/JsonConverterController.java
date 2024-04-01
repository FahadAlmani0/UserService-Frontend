package rosol.userservice.controllers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import rosol.userservice.models.AppUser;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller class to manage the serialization and deserialization of AppUser objects using ObjectMapper
 */
public class JsonConverterController {

    private final ObjectMapper mapper;

    public JsonConverterController() {
        this.mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(new UserObjectSerializer());
        module.addDeserializer(AppUser.class, new UserObjectDeserializer());
        mapper.registerModule(module);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

}

/**
 * Serializer class for AppUser objects
 */
class UserObjectSerializer extends StdSerializer<AppUser> {

    public UserObjectSerializer() {
        this(AppUser.class);
    }

    protected UserObjectSerializer(Class<AppUser> t) {
        super(t);
    }

    @Override
    public void serialize(AppUser appUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        //serializing AppUser fields
        jsonGenerator.writeStringField("id", appUser.getId().toString());
        jsonGenerator.writeStringField("name", appUser.getName());
        jsonGenerator.writeStringField("lastname", appUser.getLastname());
        jsonGenerator.writeStringField("dateOfBirth", appUser.getDateOfBirth().toString());
        jsonGenerator.writeStringField("gender", String.valueOf(appUser.getGender()));
        jsonGenerator.writeStringField("nationality", appUser.getNationality());
        jsonGenerator.writeStringField("phone", appUser.getPhone());
        jsonGenerator.writeStringField("email", appUser.getEmail());
        jsonGenerator.writeStringField("username", appUser.getUsername());
        jsonGenerator.writeStringField("password", appUser.getPassword());
        jsonGenerator.writeStringField("entityId", appUser.getEntityId());
        jsonGenerator.writeStringField("nationalId", String.valueOf(appUser.getNationalId()));
        jsonGenerator.writeStringField("role", String.valueOf(appUser.getRole()));
        jsonGenerator.writeStringField("permission", String.valueOf(appUser.getPermission()));
        jsonGenerator.writeStringField("missionId", appUser.getMissionId());

        jsonGenerator.writeEndObject();
    }
}

/**
 * Deserializer class for AppUser objects
 */
class UserObjectDeserializer extends StdDeserializer<AppUser>{

    public UserObjectDeserializer() {
        this(AppUser.class);
    }

    protected UserObjectDeserializer(Class<AppUser> t) {
        super(t);
    }

    @Override
    public AppUser deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        AppUser user = new AppUser();

        //deserializing AppUser fields
        user.setId(Long.parseLong(node.get("id").asText()));
        user.setName(node.get("name").asText());
        user.setLastname(node.get("lastname").asText());
        user.setDateOfBirth(LocalDate.parse(node.get("dateOfBirth").asText()));
        user.setGender(node.get("gender").asText().charAt(0));
        user.setNationality(node.get("nationality").asText());
        user.setPhone(node.get("phone").asText());
        user.setEmail(node.get("email").asText());
        user.setUsername(node.get("username").asText());
        user.setPassword(node.get("password").asText());
        user.setEntityId(node.get("entityId").asText());
        user.setNationalId(node.get("nationalId").asInt());
        user.setRole(node.get("role").asText());
        user.setPermission(node.get("permission").asInt());
        user.setMissionId(node.get("missionId").asText());

        return user;
    }
}
