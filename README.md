# CasaGHome
Helper library to integrate Google Home in your IoT Platform.

This library model devices and traits exposed from Google Home. It implements callbacks to execution from Google Home and send status and notification to Google Home.

## Dependency

- Minimum JDK version: Java 22
- slf4j-api
- jackson-databind
- google-auth-library-oauth2-http
- google-api-services-homegraph


## Devices and Traits implemented

The following [devices](https://developers.home.google.com/cloud-to-cloud/guides) and [traits](https://developers.home.google.com/cloud-to-cloud/traits) are implemented:

| Device Type | Traits Implemented |
|-------------|--------------------|
| ✅ [Light](https://developers.home.google.com/cloud-to-cloud/guides/light)    | [OnOff](https://developers.home.google.com/cloud-to-cloud/traits/onoff), [Brightness](https://developers.home.google.com/cloud-to-cloud/traits/brightness), [ColorSettings](https://developers.home.google.com/cloud-to-cloud/traits/colorsettings), [Mode](https://developers.home.google.com/cloud-to-cloud/traits/mode)  |
| ✅ [Sensor](https://developers.home.google.com/cloud-to-cloud/guides/sensor)    | ⚡[SensorState](https://developers.home.google.com/cloud-to-cloud/traits/sensorstate)  |
| ✍ [Garage](https://developers.home.google.com/cloud-to-cloud/guides/garage)    | [GarageDoorState](https://developers.home.google.com/cloud-to-cloud/traits/garagedoorstate)  |
| ❌ [Smoke Detector](https://developers.home.google.com/cloud-to-cloud/guides/smokedetector)    |  |
| ❌ [Switch](https://developers.home.google.com/cloud-to-cloud/guides/switch)      |            |
| ❌ [Thermostat](https://developers.home.google.com/cloud-to-cloud/guides/thermostat)  |   |
| ......|......|


✅ Implement all Traits<br>
⚡ Can send notification<br>
✍ Implement some Traits<br>
❌ Not implemented<br>

## Prerequisite

To manage your devices via Google Home you need to setup an Authentication Server and create Google Home Project.

### Account linking

To receive Google Home API calls you need to setup the Account Linking. This allow Google Home to obtain an Access Token and a Refresh Token from your OAuth2 Identity Provider Server and to use this token (access token) to call your service on behalf of your user.

I use Keycloak to setup a my IdP Server, but you can use any OAuth2.0 server or services ([here](https://www.osys.it/googlehome/#/4) a presentation, in Italian, with some screenshots).

For more info see [here](https://developers.home.google.com/cloud-to-cloud/project/authorization).

Eventually, you need this parameters from OAuth2 platform to create the Google Home Project:
- Authorization Endpoint URL
- Token Endpoint URL
- Client ID
- Client Secret
- Scopes (optional)

### Platform endpoint

You have to create a ReST Endpoint (with ssl) where Google Home can send its requests. This library help to decoding and manage this requests.

### Google Home Project

Create Google Home Project, see [here](https://developers.home.google.com/cloud-to-cloud/project/create) ([here](https://www.osys.it/googlehome/#/5) a presentation, in Italian, with some screenshots).

## My DEMO Setup

In the [example](https://github.com/dometec/CasaGHome-examples) I uses a [Quarkus](https://quarkus.io/) (in red) web application and an instance of [Keycloak](http://keycloak.jboss.org/) started on my machine and [ngrok](https://ngrok.com/) to route requests from a public endpoint to my local service.

![Demo](MiaPiattaformaIOTDemo.jpg)

If you want simplify the setup and avoid to install Keycloak, I include in the example here a *fake* authentication, use it only as a show case.

## How use this library

This library is used inside a backend that receive http requests from Google Home to syncroniza, query or execute command to your device/IoT platform/ecc (in red in the picture above) and is able also to call Google Home to report state and notification (or alert) from your devices/platform.

### Add dependency to you project

```xml
    <dependency>
      <groupId>it.osys</groupId>
      <artifactId>casa.ghome</artifactId>
      <version>0.1.2</version>
    </dependency>
```

### Setup Library

The library is composed by two main call: 
- CasaGHome: Responsable to manage requests from Google Home and to callback your code to esecute actions.
- CasaReportStateAndNotification: To send Device change / notification or alert to Google Home.

The CasaGHome need only an ObjectMapper object, while CasaReportStateAndNotification need also an instance of [HomeGraphService](https://googleapis.dev/java/google-api-services-homegraph/latest/com/google/api/services/homegraph/v1/HomeGraphService.html), a ScheduledExecutorService and a integer that represente, in millisecond, the time to wait betweent two same update requests (for the same device and trait). This last two parameters are used to debounce call towards Google Home.

The initialization code is:
```java
casaGHome = new CasaGHome(objectMapper);
casaReport = new CasaReportStateAndNotification(homegraphService, objectMapper, scheduledExecutorService, 150);
```

You initialize CasaReportStateAndNotification you need to provide an HomeGraphService instance already configured with the service account credential. 
See [https://developers.home.google.com/cloud-to-cloud/integration/report-state#java](https://developers.home.google.com/cloud-to-cloud/integration/report-state#java)

Here is an example of Creation of HomeGraphService:<br>

```java
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.homegraph.v1.HomeGraphService;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class HomeGraphServiceProvider {

	@Produces
	public HomeGraphService getHomeGraphService() throws IOException, GeneralSecurityException {

		GoogleCredentials credentials = GoogleCredentials.fromStream(this.getClass().getResourceAsStream("/dometec-849c182e9d61.json"))
				.createScoped(List.of("https://www.googleapis.com/auth/homegraph"));

		HttpCredentialsAdapter credential = new HttpCredentialsAdapter(credentials);

		HomeGraphService.Builder builder = new HomeGraphService.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				GsonFactory.getDefaultInstance(), credential);

		return builder.setApplicationName("CasaGHome/1.0").build();

	}

}	
```


### Create your User's devices

The library models [devices](https://developers.home.google.com/cloud-to-cloud/guides) that can be use with Google Home, like light, garage, sensor, ecc. 

You have to model all devices that a single User can manage into a _Casa_ Object. Each user have an instance of _Casa_ that contains all its devices.

For each device, you can enable zero or more [Traits](https://developers.home.google.com/cloud-to-cloud/traits). A device without traits can expose only its *online* status.

You can store this data locally or on any external service like DB, Redis, Mongo, ecc. For example, in the example I store it in memory:

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.osys.casa.ghome.CasaGHome;
import jakarta.inject.Singleton;

/**
 * Store all homes/devices in JVM memory 
 */
@Singleton
public class DevicesStore {

	// AgentUserId -> Casa
	private Map<String, CasaGHome> casas = new HashMap<>();

	public void putCasa(String agentUserid, CasaGHome casa) {
		casas.put(agentUserid, casa);
	}

	public Optional<CasaGHome> getCasaFor(String agentUserId) {
		return Optional.ofNullable(casas.get(agentUserId));
	}

}

```

If your store is empty you have to fill it! Indeed, an User can have zero devices, but it's a bit useless :)<br>
This is the most important parts, how devices are defined and how you and Google Home interact with them.
Let's start with a simple Light that we can command to switch on or off and Google Home can query for its status:

```java
Light light = new Light(DEVICEIDLAMP, new DeviceName("Lampada")).withOnOffTrait(false, false);

UserDevices userDevices = new UserDevices(AGENTUSERID);
userDevices.addDevice(light);

homesDevicesTreeStore.putCasa(TEST_AGENTUSERID, casa);
```

We define a Device with its ID, NAME and zero o more optional Trait. We put it in a UserDevices that represent all User's devices and save it elsewhere (I use an im memory store like define before).

The [OnOffTrait](https://developers.home.google.com/cloud-to-cloud/traits/onoff) has two attrbitutes:
 - commandOnlyOnOff
 - queryOnlyOnOff
Setting this attribute to false (```...withOnOffTrait(false, false)```) means that the Light on/off status can be queried and commanded by Google Home.

### Receive calls from Google Home

You neet to provide an endpoint to Google Home where it can send requests:
The following example are a standard ReST endpoint using Jakarta JaxRS. The only tow thing it need are the user id and the UserDevices storage of this user. 
```java
	
	@Context
	SecurityContext securityContext;
	...
	
	@POST
	@RolesAllowed("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public ObjectNode homeRequest(RequestDto req) {
		String uuid = ((OidcJwtCallerPrincipal) securityContext.getUserPrincipal()).getClaim("sub");
		Optional<UserDevices> casa = homesDevicesTreeStore.getCasaFor(uuid);
		return casaGHome.handleRequest(req, uuid, casa);
	}
	
```

How we can receive commands from Google Home? You have to define callbacks on CasaGHome object instance.
You have to return a ```CompletableFuture<Void>``` so the response to Google Home is created when this Future is completed (beware of timeout).
Where you receive a command, you have to act on your device / IoT platform to do the command. If you receive an ACK from device in field you can wait this to resolve the completable future, or you can complete it immediately like in the following snippet:
```java
casaGHome.setOnOffTraitWantListener(new OnOffTraitWantListener() {
	@Override
	public CompletableFuture<Void> onWantOnOff(Device device, String requertId, boolean on) {

		// TODO Turn on or of the light on your platform

		// When you receive the ACK from you platform, update device
		// status and send the report state
		OnOffTrait onOffTrait = ((Light) device).getOnOffTrait();
		onOffTrait.setOn(on);

		// Send ReportState
		reportEngine.sendReportState(device, onOffTrait);

		CompletableFuture<Void> cf = new CompletableFuture<>();
		// Where we have an ACK from the field we can complete the
		// CompletableFuture
		cf.complete(null);
		return cf;
	}
});
```

Please note that you have to send ReportState not only where the state of your device change autonomously from the field (es: some one switch on the light at home) but also when the change came from Google Home, see [here](https://developers.home.google.com/cloud-to-cloud/integration/report-state) (from Docs: ***Note: Subsequent state information returned via EXECUTE and QUERY intents will not be stored in Home Graph. It is your responsibility to call Report State to update Home Graph with the latest state information.***)



# Licensing Model for the CasaGHome Library

The CasaGHome library is released under a Dual Licensing model to ensure project sustainability and allow for wide adoption.

Users must choose between one of the two following options based on their status and intended use:

## Option 1: Open Source License (Free)

This option is ideal for individual developers, non-profit projects, and companies willing to comply with the license terms.

License: GNU Affero General Public License Version 3 (AGPLv3)

Key Term:

You are free to use, modify, and distribute the Library.

CRITICAL WARNING (Copyleft Clause): If the Library is used in an application or service that is accessible over a network (e.g., a web application or SaaS), you are required to make the complete source code of your entire application available to users interacting with that service.

Who Can Use It:

Individuals and developers working on personal/hobby projects.

Non-Profit Organizations (please contact us for confirmation).

Any entity (including for-profit) willing and able to fully comply with the AGPLv3 source code sharing requirements.

## Option 2: Commercial License (Paid)

This option is necessary for companies that wish to maintain their code as proprietary.

Key Advantage: Purchasing a Commercial License exempts the user from the AGPLv3's code sharing requirements. This allows you to use the Library in proprietary applications (including SaaS) while keeping your surrounding application source code secret and proprietary.

Who is Required to Purchase: Any for-profit entity intending to integrate the Library into a product or service whose source code must remain proprietary and non-public.

Additional Benefits: Commercial licenses typically include priority technical support and indemnity guarantees.

### Contact Us for Commercial Licensing

If your organization falls under the category for a Commercial License, please contact us to discuss terms and pricing:

Email: domenico.briganti@osys.it






















