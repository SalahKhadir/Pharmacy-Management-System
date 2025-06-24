# Pharmacy Management System

A comprehensive desktop application for managing pharmacy operations, built with Java Swing and MySQL. This system provides role-based access control for administrators and pharmacists with complete inventory management, sales processing, and reporting capabilities.

![Java](https://img.shields.io/badge/Java-17+-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)
![Swing](https://img.shields.io/badge/GUI-Swing-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 🚀 Features

### 👨‍💼 Administrator Features
- **User Management**: Add, view, update, and delete system users
- **Role Assignment**: Assign Admin or Pharmacist roles
- **Sales Monitoring**: View all bills and transactions
- **System Dashboard**: Centralized control panel

### 👨‍⚕️ Pharmacist Features
- **Inventory Management**: Add, update, view, and delete medicines
- **Sales Processing**: Complete point-of-sale system with cart functionality
- **Bill Generation**: Automated PDF invoice creation
- **Stock Monitoring**: Track out-of-stock items and low inventory
- **Profile Management**: View and manage personal information

### 🔧 System Features
- **FIFO Inventory**: First-expiry-first-out automatic stock rotation
- **Real-time Search**: Dynamic medicine search and filtering
- **PDF Receipts**: Professional invoice generation with auto-print
- **Data Validation**: Comprehensive input validation and error handling
- **Secure Authentication**: Role-based login system

## 🛠️ Technologies Used

- **Frontend**: Java Swing
- **Backend**: Java SE
- **Database**: MySQL 8.0+
- **PDF Generation**: iText PDF
- **Date Components**: JCalendar
- **Database Connectivity**: JDBC

## 📋 Prerequisites

Before running this application, ensure you have:

- Java Development Kit (JDK) 17 or higher
- MySQL Server 8.0 or higher
- MySQL Workbench (optional, for database management)
- An IDE (IntelliJ IDEA, Eclipse, or NetBeans)

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd pharmacy-management-system
```

### 2. Database Setup
1. Start your MySQL server
2. Create a new database:
```sql
CREATE DATABASE gestionpharmacie;
```
3. Import the database schema:
```bash
mysql -u root -p gestionpharmacie < gestionpharmacie.sql
```

### 3. Configure Database Connection
Update the database connection details in the source files:
```java
// In Connect() methods, update these values:
connection = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/gestionpharmacie", 
    "your_username", 
    "your_password"
);
```

### 4. Add Required Libraries
Ensure these JAR files are in your classpath:
- `mysql-connector-j-8.2.0.jar`
- `jcalendar-1.4.jar`
- `itextpdf-5.5.9.jar`

### 5. Compile and Run
```bash
# Compile
javac -cp ".:lib/*" src/**/*.java

# Run
java -cp ".:lib/*:src" Main
```

## 🚀 Running the Application

1. Execute the `Main.java` file
2. The login screen will appear
3. Use default credentials:
   - **Username**: `admin`
   - **Password**: `admin`
   - **Role**: Administrator

## 📖 User Guide

### First-Time Setup
1. Login as admin using default credentials
2. Create pharmacist accounts through "Add User"
3. Add initial medicine inventory
4. System is ready for operations

### For Administrators
1. **Add Users**: Navigate to "Add User" to create new accounts
2. **View Users**: Monitor all system users and their details
3. **View Bills**: Track all sales transactions
4. **System Management**: Oversee overall system operations

### For Pharmacists
1. **Add Medicine**: Input new medicine inventory with expiry dates
2. **Update Medicine**: Modify existing medicine information
3. **Sell Medicine**: Process customer purchases with automatic billing
4. **View Stock**: Monitor current inventory levels
5. **Generate Bills**: Create PDF receipts for transactions

## 📁 Project Structure

```
pharmacy-management-system/
├── src/
│   ├── Main.java                 # Application entry point
│   ├── Login/
│   │   └── User.java            # Authentication system
│   ├── Admin/
│   │   ├── Dashboard.java       # Admin dashboard
│   │   ├── Add_user.java        # User creation
│   │   ├── view_user.java       # User management
│   │   ├── Update_user.java     # User updates
│   │   └── bills.java           # Bill viewing
│   └── Pharmacist/
│       ├── Pharmacist.java      # Pharmacist dashboard
│       ├── Add_Medicine.java    # Inventory addition
│       ├── update_medicine.java # Inventory updates
│       ├── View_Medicine.java   # Inventory viewing
│       ├── Sell_Medicine.java   # Sales processing
│       ├── bill.java            # Bill management
│       ├── OutofStock.java      # Stock monitoring
│       └── profile.java         # Profile management
├── lib/                         # External libraries
├── gestionpharmacie.sql        # Database schema
└── README.md
```

## 🔍 Database Schema

### Tables Overview
- **users**: System user management
- **medicine**: Medicine inventory with batch tracking
- **out_of_stock**: Depleted inventory tracking
- **bill**: Sales transaction records

### Key Relationships
- Medicine table uses composite key (MedicineID, ExpiryDate) for batch management
- Bills are linked to users through foreign key relationship
- Out-of-stock items maintain medicine references

## 🐛 Common Issues & Troubleshooting

### Database Connection Issues
- Verify MySQL server is running
- Check connection credentials
- Ensure database exists and schema is imported

### PDF Generation Errors
- Check if output directory exists
- Verify write permissions
- Ensure iText library is properly included

### GUI Display Issues
- Verify all image resources are available
- Check Swing compatibility with your Java version
- Ensure proper classpath configuration

## 🔐 Security Considerations

- Default admin credentials should be changed immediately
- Implement proper password policies
- Consider encrypting stored passwords
- Regular database backups recommended

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Future Enhancements

- [ ] Web-based interface
- [ ] Barcode scanning integration
- [ ] Advanced reporting and analytics
- [ ] Email notifications for low stock
- [ ] Multi-location support
- [ ] Mobile application
- [ ] Cloud database integration

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Developer**: [Your Name]
- **Project Type**: Academic/Commercial Project
- **Version**: 1.0.0

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact: [your-email@example.com]

## 🙏 Acknowledgments

- iText team for PDF generation capabilities
- MySQL team for the reliable database system
- Java Swing community for GUI components
- JCalendar library contributors

---

**Note**: This system is designed for educational and small-scale commercial use. For enterprise deployment, additional security measures and scalability considerations should be implemented.
