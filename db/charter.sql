ALTER TABLE transactions
    ADD CONSTRAINT transaction_to_customers
    FOREIGN KEY transactions(id_customer)
    REFERENCES  customers(id_customer)




CREATE USER
    username charter
IDENTIFIED BY PASSWORD
    'charter123'



GRANT ALL PRIVILEGES ON charter.* TO 'charter'@localhost IDENTIFIED BY 'charter123';