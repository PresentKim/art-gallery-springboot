CREATE OR REPLACE VIEW gallery_view AS
SELECT gseq,
       author as author_id,
       name   as author_name,
       title,
       writedate,
       content,
       readcount,
       image,
       savefilename
FROM gallery
         INNER JOIN member ON gallery.author = member.id
ORDER BY gallery.writedate DESC;



CREATE OR REPLACE VIEW favorite_view AS
SELECT id AS member_id, artwork.*
FROM favorite_artwork
         INNER JOIN artwork ON favorite_artwork.aseq = artwork.aseq
ORDER BY favorite_artwork.writedate;


-- START TOGGLE_FAVORITE_ARTWORK PROCEDURE
DROP PROCEDURE IF EXISTS toggle_favorite_artwork;

DELIMITER //

CREATE PROCEDURE toggle_favorite_artwork(
    IN input_id VARCHAR(45),
    IN input_aseq INT UNSIGNED,
    OUT result BOOLEAN
)
BEGIN
    DECLARE value_exists INT;

    START TRANSACTION;

    SELECT COUNT(*)
    INTO value_exists
    FROM favorite_artwork
    WHERE id = input_id
      AND aseq = input_aseq;

    IF value_exists > 0 THEN
        DELETE
        FROM favorite_artwork
        WHERE id = input_id
          AND aseq = input_aseq;
    ELSE
        INSERT INTO favorite_artwork (id, aseq)
        VALUES (input_id, input_aseq);
    END IF;

    SET result = value_exists <= 0;
    COMMIT;
END //

DELIMITER ;

-- END TOGGLE_FAVORITE_ARTWORK PROCEDURE