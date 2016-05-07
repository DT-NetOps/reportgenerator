# reportgenerator

NATIVE ANDROID APP in ANDROID STUDIO

PURPOSE
Application with list of clients and their details which include container(s) + item(s) based on barcodes scanned with the camera and stored in the local database.
It has to create pdf file with all scanned container(s) + item(s), total quantities of each as well as client details such as Name, Address, Email, Phone, Comment, Collection Date which will be send stored or typed email.

SCREENS
Mobile app will include screens such as:
•	Main screen – shows clients, allows adding new ones.
•	Add Client screen – allows to add client details.
•	Client Details screen – shows client, its container(s) and details, allows adding new container(s).
•	Add Container screen – allows adding container with barcode and its details.
•	View Container screen – allows to view container details and adding it more items with barcode as well as its details.
•	PDF Report screen – displays details of the report.
•	Send Report screen – allows sending an email with report to specific address.

MODULES
For the purpose of the initial project proposal we are going to refer to single functionality as “module” which during the development process will be replaced with specific “activities” implemented by “classes”.
At this point high overview of main functionality distinguishes below modules:
•	Database module – stores users and clients’ information in internal database (SQLite).
•	Barcode module – uses camera to read barcodes for creation of the report (ZXing).
•	PDF module – uses database to generate the report (iText).
•	Confirmation module – sends auto confirmation message to user and client.
