# WhatsApp Outgoing Messages API

## Overview
This API allows you to send outgoing WhatsApp messages programmatically using the WhatsApp Business API.

## Base URL
```
http://localhost:8902/chatbot/api/v1/messages
```

## Endpoints

### 1. Send Message (JSON Body)

**Endpoint:** `POST /send`

**Description:** Send a WhatsApp message using JSON request body.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
    "to": "919876543210",
    "message": "Hello from WhatsApp Bot!",
    "preview_url": false
}
```

**Parameters:**
- `to` (string, required): Recipient phone number in international format (e.g., 919876543210)
- `message` (string, required): Message content to send
- `preview_url` (boolean, optional): Enable URL preview in message (default: false)

**Response:**
```json
{
    "success": true,
    "message": "Message sent successfully",
    "recipient": "919876543210",
    "timestamp": "2025-11-20T10:30:45.123"
}
```

**Status Codes:**
- `200 OK` - Message sent successfully
- `400 Bad Request` - Invalid request parameters
- `500 Internal Server Error` - Server error occurred

**Example cURL:**
```bash
curl -X POST http://localhost:8902/chatbot/api/v1/messages/send \
  -H "Content-Type: application/json" \
  -d '{
    "to": "919876543210",
    "message": "Hello from WhatsApp Bot!",
    "preview_url": false
  }'
```

---

### 2. Send Quick Message (Query Parameters)

**Endpoint:** `POST /send-quick`

**Description:** Send a WhatsApp message using query parameters (simplified version).

**Query Parameters:**
- `to` (string, required): Recipient phone number
- `message` (string, required): Message content

**Response:**
```json
{
    "success": true,
    "message": "Message sent successfully",
    "recipient": "919876543210",
    "timestamp": "2025-11-20T10:30:45.123"
}
```

**Example cURL:**
```bash
curl -X POST "http://localhost:8902/chatbot/api/v1/messages/send-quick?to=919876543210&message=Hello%20World"
```

---

### 3. Health Check

**Endpoint:** `GET /health`

**Description:** Check if the outgoing message service is running.

**Response:**
```
Outgoing Message Service is running
```

**Status Code:** `200 OK`

**Example cURL:**
```bash
curl -X GET http://localhost:8902/chatbot/api/v1/messages/health
```

---

## Configuration

The API uses the following configuration from `application.properties`:

```properties
# WhatsApp Configuration
whatsapp.access.token=<your-access-token>
whatsapp.phone.number.id=<your-phone-number-id>
whatsapp.api.url=https://graph.facebook.com/v18.0
```

Make sure these properties are configured correctly before using the API.

---

## Error Handling

All endpoints return a consistent error response format:

```json
{
    "success": false,
    "message": "Error description",
    "recipient": "919876543210",
    "timestamp": "2025-11-20T10:30:45.123"
}
```

Common errors:
- Missing or empty recipient phone number
- Missing or empty message body
- Invalid WhatsApp credentials
- Network connectivity issues

---

## Testing with Postman

A Postman collection is available at `Outgoing_Messages_API.postman_collection.json`.

To use it:
1. Import the collection into Postman
2. Update the phone number in the requests
3. Ensure your Spring Boot application is running
4. Execute the requests

---

## Architecture

The outgoing messages API consists of:

1. **OutgoingMessageController** - REST endpoints
2. **OutgoingMessageService** - Business logic
3. **WhatsAppApiClient** - HTTP client for WhatsApp API
4. **DTOs** - Request/Response data transfer objects
   - SendMessageRequest
   - SendMessageResponse
   - WhatsAppMessageRequest

---

## Phone Number Format

Phone numbers should be in international format without the '+' symbol:
- ✅ Correct: `919876543210` (India)
- ✅ Correct: `14155551234` (USA)
- ❌ Incorrect: `+919876543210`
- ❌ Incorrect: `9876543210`

---

## Rate Limiting

Be aware of WhatsApp Business API rate limits:
- Messages per second vary based on your tier
- Monitor your usage to avoid throttling
- Check the WhatsApp Business API documentation for current limits

---

## Security Considerations

1. **Access Token Security**: Never expose your WhatsApp access token in client-side code
2. **Authentication**: Consider adding authentication to these endpoints in production
3. **Input Validation**: All inputs are validated before processing
4. **Logging**: Sensitive information is not logged

---

## Next Steps

To enhance this API, consider:
1. Adding authentication/authorization
2. Implementing message templates
3. Adding support for media messages (images, videos, documents)
4. Implementing message queuing for high volume
5. Adding webhook integration for delivery status
6. Rate limiting at the application level

---

## Support

For issues or questions:
1. Check the application logs
2. Verify WhatsApp credentials
3. Test with the health check endpoint
4. Review the WhatsApp Business API documentation

