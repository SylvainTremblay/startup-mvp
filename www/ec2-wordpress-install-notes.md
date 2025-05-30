To install WordPress on the **Amazon EC2 Free Tier**, follow these step-by-step instructions. We'll use the **Ubuntu server** and install the **LAMP stack** (Linux, Apache, MySQL, PHP), which is a common setup for WordPress.

---

### ✅ Prerequisites

* AWS account
* Free Tier eligible EC2 instance (e.g., **t2.micro** or **t3.micro**)
* Basic knowledge of SSH and Linux terminal

---

## 🚀 Step-by-Step Installation Guide

---

### **1. Launch an EC2 Instance**

1. Go to the [EC2 Dashboard](https://console.aws.amazon.com/ec2/)
2. Click **Launch Instance**
3. Choose:

   * **Amazon Machine Image (AMI):** Ubuntu Server 22.04 LTS (free tier eligible)
   * **Instance Type:** t2.micro
   * Configure rest (defaults are usually fine)
4. Create or use an existing key pair
5. Launch the instance

---

### **2. Connect to Your EC2 Instance**

Use SSH to connect:

```bash
ssh -i /path/to/your-key.pem ubuntu@your-ec2-public-ip
```

---

### **3. Update the System**

```bash
sudo apt update && sudo apt upgrade -y
```

---

### **4. Install Apache**

```bash
sudo apt install apache2 -y
sudo systemctl enable apache2
sudo systemctl start apache2
```

Test it: open `http://your-ec2-public-ip` in a browser. You should see the Apache welcome page.

---

### **5. Install MySQL**

```bash
sudo apt install mysql-server -y
sudo mysql_secure_installation
```

During the prompt, set a root password and secure your installation.

---

### **6. Create a MySQL Database for WordPress**

```bash
sudo mysql -u root -p
```

Then run the following SQL commands:

```sql
CREATE DATABASE wordpress;
CREATE USER 'wpuser'@'localhost' IDENTIFIED BY 'strongpassword';
GRANT ALL PRIVILEGES ON wordpress.* TO 'wpuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

---

### **7. Install PHP**

```bash
sudo apt install php libapache2-mod-php php-mysql php-cli php-curl php-gd php-mbstring php-xml php-xmlrpc php-soap php-intl php-zip -y
```

Restart Apache:

```bash
sudo systemctl restart apache2
```

---

### **8. Download WordPress**

```bash
cd /tmp
curl -O https://wordpress.org/latest.tar.gz
tar xzvf latest.tar.gz
```

---

### **9. Configure WordPress Files**

```bash
sudo rsync -avP /tmp/wordpress/ /var/www/html/
```

Set correct permissions:

```bash
sudo chown -R www-data:www-data /var/www/html/
sudo chmod -R 755 /var/www/html/
```

---

### **10. Configure WordPress**

Copy sample config and edit:

```bash
cd /var/www/html
sudo cp wp-config-sample.php wp-config.php
sudo nano wp-config.php
```

Update DB settings:

```php
define( 'DB_NAME', 'wordpress' );
define( 'DB_USER', 'wpuser' );
define( 'DB_PASSWORD', 'strongpassword' );
define( 'DB_HOST', 'localhost' );
```

---

### **11. Final Setup in Browser**

Go to: `http://your-ec2-public-ip`

You should see the WordPress installation screen. Follow the wizard to set up your admin account and site.

---

### ✅ Optional: Configure Firewall (if needed)

Allow HTTP:

```bash
sudo ufw allow 'Apache Full'
```

Also, ensure your EC2 Security Group allows **HTTP (port 80)** and **SSH (port 22)**.

---

Let me know if you'd like a script to automate all this!

