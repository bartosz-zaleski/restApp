
GRANT ALL PRIVILEGES ON charter.* TO 'charter'@localhost IDENTIFIED BY 'charter123';


ALTER TABLE transactions
    ADD CONSTRAINT transaction_to_customers
    FOREIGN KEY transactions(id_customer)
    REFERENCES  customers(id_customer)




CREATE USER
    username charter
IDENTIFIED BY PASSWORD
    'charter123'


BEGIN

INSERT INTO customers(firstname, surname, email)
    SELECT customer_firstname, customer_surname, customer_email FROM DUAL
    WHERE NOT EXISTS(SELECT 1 FROM customers WHERE firstname=customer_firstname AND surname=customer_surname AND email=customer_email LIMIT 1);


    # Obtain the id_customer
    SELECT @id_customer:=id_customer FROM customers WHERE firstname=customer_firstname AND surname=customer_surname AND email=customer_email LIMIT 1;

    # Insert
    INSERT INTO transactions(id_customer, date, points_earned) VALUES (@id_customer, NOW(), transaction_points_earned);
END








