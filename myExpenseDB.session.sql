
SELECT * From transactions;

SELECT * From Users;

SHOW CREATE TABLE transactions;
ALTER TABLE transactions DROP FOREIGN KEY fk_transactions_user_id;
ALTER TABLE transactions DROP FOREIGN KEY fk_user_id;

ALTER TABLE users CHANGE user_id username VARCHAR(255) NOT NULL;

ALTER TABLE transactions 
ADD CONSTRAINT fk_transactions_user_id 
FOREIGN KEY (user_id) REFERENCES users(username);


