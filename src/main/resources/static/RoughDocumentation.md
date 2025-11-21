
## Team BroCode

- Topic : Whatsapp Bot 

EndUser -> Meta/EC2 -> Webhook -> AWS(EC2 Instance ) -> NLP/AI Layer (AWS) -> AWS DB Layer(Mocked Data)

---

## ✅ Project Structure Implemented

```
src/main/java/com/hackathon/lending/
├── bot/                                 # Application Entry Point
│   └── ChatbotApplication.java
├── config/                              # Configuration Classes
│   ├── JacksonConfig.java              # JSON serialization config
│   └── WebConfig.java                  # CORS configuration
├── controller/                          # REST Controllers
│   └── WebhookController.java          # Meta Webhook Handler
├── dto/                                 # Data Transfer Objects
│   ├── MessageContext.java             # Message context wrapper
│   ├── WhatsAppMessageRequest.java     # Outgoing message structure
│   └── WhatsAppWebhookRequest.java     # Incoming webhook structure
├── entity/                              # JPA Entities (Database Tables)
│   ├── ChatHistory.java                # Chat history records
│   ├── Message.java                    # Message storage
│   ├── StageTracker.java               # User journey stage tracking
│   └── UserDetails.java                # User information
├── repository/                          # Data Access Layer
│   ├── ChatHistoryRepository.java
│   ├── MessageRepository.java
│   ├── StageTrackerRepository.java
│   └── UserDetailsRepository.java
├── service/                             # Business Logic Layer
│   ├── LendingWorkflowService.java     # Loan workflow handlers
│   └── WhatsAppMessageService.java     # Message processing
└── utility/                             # Utility Classes
    ├── ApplicationStages.java          # Stage constants
    ├── MessageParser.java              # Message parsing
    └── WhatsAppApiClient.java          # WhatsApp API client
```

---

### Database Design (Implemented)
1. **chat_history** -> (mobile_number(PK), name, message_id, time_stamp, message_type)
2. **messages** -> (message_id(PK), message, created_at, updated_at)
3. **user_details** -> (mobile_id(PK), application_id, name, pan, aadhaar)
4. **stage_tracker** -> (id(PK), application_id, mobile_id, current_stage, last_updated)

---

### Current Scope (✅ IMPLEMENTED)
- ✅ Onboarding 
- ✅ Create Application 
- ✅ Update Application 
- ✅ KYC 
- ✅ Eligibility
- ✅ Offer
- ✅ Documents Verification
- ✅ Disbursal 
- ✅ Post Disbursal Flow (repayment and all status)

---

### Webhook Endpoints

**1. GET /webhook** - Meta webhook verification
- Verifies your webhook with Meta's challenge/verify_token

**2. POST /webhook** - Receive incoming WhatsApp messages  
- Processes all incoming user messages from WhatsApp

**3. GET /webhook/health** - Health check
- Verify service is running

---

### Key Features Implemented

✅ **Complete Meta WhatsApp Integration**
- Webhook verification and message receiving
- Message sending via WhatsApp Business API
- Proper JSON structure for Meta API

✅ **Multi-Stage Conversational Flow**
- 8 distinct stages from onboarding to post-disbursal
- Context-aware responses based on user stage
- Automatic stage progression

✅ **Database Persistence**
- Chat history tracking
- Message storage
- User details management
- Stage tracking

✅ **Workflow Management**
- Loan application creation with unique IDs
- KYC data collection (PAN, Aadhaar)
- Eligibility checking
- Offer presentation and acceptance
- Document verification
- Disbursal tracking
- Post-disbursal status checking

✅ **Error Handling**
- Graceful error handling
- User-friendly error messages
- Automatic webhook acknowledgment

---

### Configuration Required

Update `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/whatsapp_bot
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

# Meta WhatsApp
whatsapp.verify.token=YOUR_CUSTOM_VERIFY_TOKEN
whatsapp.access.token=YOUR_META_ACCESS_TOKEN
whatsapp.phone.number.id=YOUR_PHONE_NUMBER_ID
whatsapp.business.account.id=YOUR_BUSINESS_ACCOUNT_ID
```

**How to get Meta credentials:**
1. Go to https://business.facebook.com
2. Select your business account
3. Navigate to WhatsApp > API Setup
4. Copy Phone Number ID and Access Token
5. Create a custom verify token
6. Configure webhook URL with your verify token

---

### Deployment Steps

1. **Setup Database**
   ```bash
   mysql -u root -p
   CREATE DATABASE whatsapp_bot;
   ```

2. **Build Project**
   ```bash
   mvn clean install
   ```

3. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Configure Meta Webhook**
   - Webhook URL: `https://your-domain.com/webhook`
   - Verify Token: (same as in application.properties)
   - Subscribe to: messages

5. **Test**
   - Send message to your WhatsApp Business number
   - Check logs to see webhook received
   - Bot should respond automatically

---

### Sample Conversation Flow

```
User: Hi
Bot: Welcome to our lending service! 🏦
     We help you get loans quickly and easily.
     To get started, please reply with your full name.

User: John Doe
Bot: Thank you, John Doe! ✅
     Now let's create your loan application.
     Would you like to:
     1. Apply for a Personal Loan
     2. Apply for a Business Loan
     Please reply with 1 or 2.

User: 1
Bot: Great! Your application ID is: APP12345678
     Now let's complete your KYC process.
     Please provide your PAN number:

User: ABCDE1234F
Bot: Thank you! Now please provide your Aadhaar number:

User: 123456789012
Bot: KYC details saved successfully! ✅
     Checking your eligibility... Please wait.
     
Bot: Great news! You are eligible for a loan! 🎉
     Based on your profile, we can offer you:
     💰 Loan Amount: ₹5,00,000
     📊 Interest Rate: 12% per annum
     ⏰ Tenure: Up to 36 months
     Would you like to proceed with this offer?
     Reply with YES or NO.

... (continues through all stages)
```

---

### Future Scope 
- Extend to multiple communication channels (SMS, email, push notification)
- FAQs identification and caching 
- Integration with actual NLP/AI service (AWS Comprehend, Dialogflow)
- OCR for document verification
- Payment gateway integration
- Advanced analytics dashboard
- Multi-language support
- Rich media messages (images, documents, buttons)

---

### Tech Stack
- **Framework**: Spring Boot 4.0.0-SNAPSHOT
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: Hibernate / Spring Data JPA
- **API**: Meta WhatsApp Business API (Graph API v18.0)
- **Build Tool**: Maven

---

### Documentation
See `README.md` for detailed setup instructions and API documentation.
