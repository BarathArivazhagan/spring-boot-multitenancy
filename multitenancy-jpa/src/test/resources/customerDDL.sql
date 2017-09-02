
CREATE SCHEMA tenant_a ;

CREATE SCHEMA tenant_b ;

SET SCHEMA  tenant_a; 


CREATE TABLE tenant_a.customer (
	customer_id INT NOT NULL,
	customer_name VARCHAR(45) NULL,
  	PRIMARY KEY (customer_id) 
 );
 
 
INSERT INTO tenant_a.customer VALUES(1,'RAMESH');
INSERT INTO tenant_a.customer VALUES(2,'SURESH');
INSERT INTO tenant_a.customer VALUES(3,'MAHESH');



SET SCHEMA  tenant_b; 

CREATE TABLE tenant_b.customer (
  customer_id INT NOT NULL,
  customer_name VARCHAR(45) NULL,
  PRIMARY KEY (customer_id)
);
  
INSERT INTO tenant_b.customer VALUES(1,'BILL');
INSERT INTO tenant_b.customer VALUES(2,'HAYDEN');
INSERT INTO tenant_b.customer VALUES(3,'STEVE');

