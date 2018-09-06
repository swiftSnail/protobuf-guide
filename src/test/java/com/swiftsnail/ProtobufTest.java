package com.swiftsnail;

import com.google.common.collect.ImmutableMap;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.swiftsnail.proto.generated.BidResponse;
import com.swiftsnail.proto.generated.People;
import com.swiftsnail.proto.generated.PrivateBid;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProtobufTest {

  @Test
  public void testAny() throws InvalidProtocolBufferException {
    PrivateBid privateBid = PrivateBid.newBuilder().setCost(123).build();
    BidResponse bidResponse = BidResponse.newBuilder().setAnyData(Any.pack(privateBid)).build();

    Assert.assertEquals(123, bidResponse.getAnyData().unpack(PrivateBid.class).getCost());
  }

  @Test
  public void testOneOf() {
    People people =
        People.newBuilder().setName("protobuf").setQq("12345").setWebChat("54321").build();
    Assert.assertEquals("", people.getQq());
    Assert.assertEquals("54321", people.getWebChat());
  }

  @Test
  public void testMap() {
    People people =
        People.newBuilder().putAllWebsiteUsername(ImmutableMap.of("netease", "swiftsnail")).build();

    Assert.assertEquals(1, people.getWebsiteUsernameCount());
  }

  @Test
  public void test2Json() throws IOException {
    String peopleInJson =
        "{\"name\": \"swiftsnail\",\"website_username\": [{\"key\": \"netease\",\"value\": \"swiftsnail\"},{\"key\": \"webchat\",\"value\": \"balabala\"}]}";

    People people =
        People.newBuilder()
            .setName("swiftsnail")
            .putAllWebsiteUsername(ImmutableMap.of("netease", "swiftsnail", "webchat", "balabala"))
            .build();
    JsonFormat jsonFormat = new JsonFormat();
    Assert.assertEquals(peopleInJson, jsonFormat.printToString(people));

    People.Builder newBuilder = People.newBuilder();
    jsonFormat.merge(
        new ByteArrayInputStream(peopleInJson.getBytes(StandardCharsets.UTF_8)),
        StandardCharsets.UTF_8,
        newBuilder);
    people = newBuilder.build();

    Assert.assertEquals("swiftsnail", people.getName());
  }
}
