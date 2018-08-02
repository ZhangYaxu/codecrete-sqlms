SELECT
  User.id,
  User.companyId,
  User.email,
  User.email AS username,
  User.forename,
  User.surname,
  User.created,
  User.modified,
  TRUE as enabled
FROM User
WHERE User.email = ?
AND User.deleted IS NULL;