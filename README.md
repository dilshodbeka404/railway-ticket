# 🚂 Railway Ticket Checker

A Spring Boot application that automatically monitors railway ticket availability on Uzbekistan Railways (eticket.railway.uz) and sends notifications via Telegram when tickets become available.

## 📋 Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [How to Run](#how-to-run)
- [Customization](#customization)
- [Available Stations](#available-stations)
- [Deployment Options](#deployment-options)
- [Troubleshooting](#troubleshooting)

## ✨ Features

- 🔍 Automatic ticket availability checking every 15 seconds
- 📱 Telegram notifications when tickets are found
- 🚆 Support for all major railway stations in Uzbekistan
- 💺 Shows available seats by car type (Economy, Business, Platzkart, etc.)
- 💰 Displays ticket prices
- ⏱ Shows departure/arrival times and travel duration
- 🔄 Configurable search routes and dates

## 📦 Prerequisites

- **Java 25** or higher
- **Maven 4.0.0** or higher
- **Telegram Bot Token** (see [Creating a Telegram Bot](#creating-a-telegram-bot))
- **Internet connection**

## 🤖 Creating a Telegram Bot

1. Open Telegram and search for `@BotFather`
2. Send `/newbot` command
3. Follow the instructions to create your bot
4. Copy the **bot token** provided by BotFather
5. Start a chat with your new bot
6. Get your **chat ID**:
   - Send any message to your bot
   - Visit: `https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates`
   - Find your `chat_id` in the response

## ⚙️ Configuration

### 1. Application Configuration

Edit `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: railway-ticket-application

server:
  port: 9193

telegram:
  bot-token: "YOUR_BOT_TOKEN"  # Replace with your bot token
  chat-id:
    - "CHAT_ID_1"  # Replace with your chat ID
    - "CHAT_ID_2"  # Add more chat IDs if needed
    # - "CHAT_ID_3"
```

**Configuration Options:**

- `server.port`: Application port (default: 9193)
- `telegram.bot-token`: Your Telegram bot token from BotFather
- `telegram.chat-id`: List of Telegram chat IDs to receive notifications

### 2. Search Routes Configuration

Edit `src/main/java/uz/railway/ticket/scheduler/TicketScheduler.java`:

```java
@Scheduled(fixedDelay = 15_000)  // Check every 15 seconds
public void checkTickets(){
    // Outbound journey
    this.search("2025-12-30", Station.TASHKENT, Station.MISKIN);
    
    // Return journey
    this.search("2026-01-04", Station.MISKIN, Station.TASHKENT);
    
    // Add more routes as needed:
    // this.search("2025-12-31", Station.SAMARKAND, Station.BUKHARA);
}
```

**Parameters:**
- **Date format**: `"YYYY-MM-DD"`
- **From/To stations**: Use `Station` enum values
- **Check interval**: `fixedDelay` in milliseconds (15000 = 15 seconds)

## 🏃 How to Run

### Method 1: Using Maven (Development)

```bash
# Navigate to project directory
cd railway-ticket

# Run the application
mvn spring-boot:run
```

### Method 2: Build and Run JAR

```bash
# Build the project
mvn clean package

# Run the JAR file
java -jar target/railway-ticket-1.0.jar
```

### Method 3: Using IDE

1. Open project in IntelliJ IDEA / Eclipse / VS Code
2. Navigate to `src/main/java/uz/railway/ticket/RailwayTicketApplication.java`
3. Run the main method

### Method 4: Background Process (Linux/Mac)

```bash
# Build the project
mvn clean package

# Run in background
nohup java -jar target/railway-ticket-1.0.jar > railway-ticket.log 2>&1 &

# Check if running
ps aux | grep railway-ticket

# Stop the process
kill <PROCESS_ID>
```

### Method 5: Using systemd (Linux)

Create `/etc/systemd/system/railway-ticket.service`:

```ini
[Unit]
Description=Railway Ticket Checker
After=network.target

[Service]
Type=simple
User=your-username
WorkingDirectory=/path/to/railway-ticket
ExecStart=/usr/bin/java -jar /path/to/railway-ticket/target/railway-ticket-1.0.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Commands:
```bash
# Reload systemd
sudo systemctl daemon-reload

# Start service
sudo systemctl start railway-ticket

# Enable on boot
sudo systemctl enable railway-ticket

# Check status
sudo systemctl status railway-ticket

# View logs
sudo journalctl -u railway-ticket -f
```

## 🎨 Customization

### Change Check Interval

Edit the `@Scheduled` annotation in `TicketScheduler.java`:

```java
@Scheduled(fixedDelay = 30_000)  // 30 seconds
// or
@Scheduled(fixedDelay = 60_000)  // 1 minute
// or
@Scheduled(cron = "0 */5 * * * *")  // Every 5 minutes
```

### Add Multiple Routes

```java
@Scheduled(fixedDelay = 15_000)
public void checkTickets(){
    // Route 1: Tashkent → Samarkand
    this.search("2025-12-30", Station.TASHKENT, Station.SAMARKAND);
    
    // Route 2: Samarkand → Bukhara
    this.search("2025-12-31", Station.SAMARKAND, Station.BUKHARA);
    
    // Route 3: Bukhara → Tashkent
    this.search("2026-01-05", Station.BUKHARA, Station.TASHKENT);
}
```

### Customize Notification Message

Edit `src/main/java/uz/railway/ticket/service/MessageFormatter.java`:

```java
public String formatTicketMessage(TrainDto train) {
    StringBuilder message = new StringBuilder();
    message.append("🚂 TICKET FOUND!\n\n");
    // Customize your message format here
    // ...
    return message.toString();
}
```

### Add More Telegram Recipients

In `application.yml`:

```yaml
telegram:
  bot-token: "YOUR_BOT_TOKEN"
  chat-id:
    - "123456789"      # User 1
    - "987654321"      # User 2
    - "-1001234567890" # Group chat
```

## 🚉 Available Stations

| Station Name | Enum Value | Code |
|--------------|------------|------|
| Tashkent | `Station.TASHKENT` | 2900000 |
| Samarkand | `Station.SAMARKAND` | 2900700 |
| Bukhara | `Station.BUKHARA` | 2900800 |
| Andijan | `Station.ANDIJAN` | 2900680 |
| Fergana | `Station.MARGILAN` | 2900920 |
| Guliston | `Station.GULISTON` | 2900850 |
| Jizzakh | `Station.JIZZAKH` | 2900720 |
| Karshi | `Station.QARSHI` | 2900750 |
| Kokand | `Station.KOKAND` | 2900880 |
| Namangan | `Station.NAMANGAN` | 2900940 |
| Navoiy | `Station.NAVOIY` | 2900930 |
| Nukus | `Station.NUKUS` | 2900970 |
| Termez | `Station.TERMEZ` | 2900255 |
| Urgench | `Station.URGENCH` | 2900790 |
| Khiva | `Station.KHIVA` | 2900172 |
| Miskin | `Station.MISKIN` | 2900104 |
| Pap | `Station.PAP` | 2900693 |

### Adding New Stations

If you need a station not listed above, add it to `Station.java`:

```java
NEW_STATION("Station Name", "STATION_CODE"),
```

Find station codes at: https://eticket.railway.uz

## 🚀 Deployment Options

### Docker Deployment

Create `Dockerfile`:

```dockerfile
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY target/railway-ticket-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
# Build JAR
mvn clean package

# Build Docker image
docker build -t railway-ticket .

# Run container
docker run -d --name railway-ticket-app railway-ticket

# View logs
docker logs -f railway-ticket-app

# Stop container
docker stop railway-ticket-app
```

### Docker Compose

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  railway-ticket:
    build: .
    container_name: railway-ticket-app
    restart: unless-stopped
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    volumes:
      - ./logs:/app/logs
```

Run:
```bash
docker-compose up -d
```

### Cloud Deployment

#### Heroku

1. Create `Procfile`:
```
web: java -jar target/railway-ticket-1.0.jar
```

2. Deploy:
```bash
heroku create railway-ticket-checker
git push heroku main
```

#### Railway.app / Render.com

1. Connect your GitHub repository
2. Set build command: `mvn clean package`
3. Set start command: `java -jar target/railway-ticket-1.0.jar`
4. Add environment variables for configuration

## 🔧 Troubleshooting

### Issue: "No tickets found"

**Solutions:**
- Check if the date format is correct (YYYY-MM-DD)
- Verify station codes are correct
- Ensure the route exists on the specified date
- Check internet connection

### Issue: "Telegram messages not sending"

**Solutions:**
- Verify bot token is correct
- Ensure chat ID is correct (include `-` for group chats)
- Check if bot is blocked by user
- Start a conversation with the bot first

### Issue: "API request errors"

**Solutions:**
- Check if eticket.railway.uz is accessible
- Verify the API URL hasn't changed
- Check firewall/proxy settings
- Review cookies and headers in `RailwayApiService.java`

### Issue: "Application stops unexpectedly"

**Solutions:**
- Check logs: `tail -f railway-ticket.log`
- Increase Java heap size: `java -Xmx512m -jar railway-ticket-1.0.jar`
- Use systemd service for auto-restart

### Issue: "Too many requests"

**Solutions:**
- Increase the check interval (e.g., 30 seconds or 1 minute)
- Add delays between searches: `Thread.sleep(Duration.ofSeconds(5))`

## 📝 Logs

Application logs show:
- Search attempts
- Tickets found/not found
- Telegram message delivery status
- API errors

View logs:
```bash
# If running in terminal
# Logs appear in console

# If using nohup
tail -f railway-ticket.log

# If using systemd
sudo journalctl -u railway-ticket -f
```

## 🤝 Contributing

Feel free to fork and customize this project for your needs!

## 📄 License

This project is open source and available for personal use.

## ⚠️ Disclaimer

This application is for personal use only. Please respect the railway service's terms of use and avoid excessive API requests.

---

**Happy ticket hunting! 🎫**
