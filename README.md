# contact-entry-system
Stores and gets the contact list

To run the program follow below steps on windows command line on C drive:

mkdir temp

cd temp

git clone https://github.com/doshigaurav21/contact-entry-system.git

cd contact-entry-system

gradlew bootjar

cd build/libs

java -jar contact-entry-system.jar

The application is successfully started. now go to PostMan at url 'http://localhost:8585' and execute below api's

Database console will be at 'http://localhost:8585/h2-console'

HTTP       Method Route	                   Description
GET	    /contacts	        	List all contacts
POST	    /contacts	        	Create a new contact
PUT	    /contacts/{id}	    	Update a contact
GET	    /contacts/{id}	    	Get a specific contact
DELETE	    /contacts/{id}	    	Delete a contact
GET	    /contacts/call-list		Get a call list: The call list is generated from all contacts that include a home phone.  It is sorted first by the 					contact’s last name, then by first name, and returned as an array of objects 

Eg:
{
  "name": {
    "first": "Harold",
    "middle": "Francis",
    "last": "Gilkey"
  },
  "address": {
    "street": "8360 High Autumn Row",
    "city": "Cannon",
    "state": "Delaware",
    "zip": "19797"
  },
  "phone": [
    {
      "number": "302-611-9148",
      "type": "home"
    },
    {
      "number": "302-532-9427",
      "type": "mobile"
    },
    {
       "number": "345-532-9427",
       "type": "work"
    }
  ],
  "email": "harold.gilkey@yahoo.com"
}
