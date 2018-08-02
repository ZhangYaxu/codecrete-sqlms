SELECT
  User.email AS username,
  User.password AS password,
  TRUE as enabled
FROM domain.User
WHERE User.deleted IS NULL
AND User.email = ?