SELECT * FROM chat.user

SELECT * FROM chat.mes
SELECT * FROM chat.groupschat
SELECT * FROM chat.groupmember

UPDATE Mes SET CONTENT = ".audio" WHERE ID_MES = 65

INSERT INTO chat.groupmember(ID_GROUP_PKMEM, ID_USER_PKMEM) VALUE (1, 2)

ALTER TABLE mes MODIFY content LONGTEXT;

DELETE FROM chat.user WHERE ID = 5

CREATE USER 'chat_user'@'%' IDENTIFIED BY 'password1234';
GRANT ALL PRIVILEGES ON *.* TO 'chat_user'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

ALTER TABLE `chat`.`groups` 
DROP COLUMN `GR_IMG_URL`;

UPDATE chat.groupschat
SET GR_AVT = (
    SELECT GR_AVT
    FROM (
        SELECT GR_AVT FROM chat.groupschat WHERE ID_GROUP = 2 LIMIT 1
    ) AS sub
)
WHERE ID_GROUP = 1;
