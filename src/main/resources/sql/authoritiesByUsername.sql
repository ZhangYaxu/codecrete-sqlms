SELECT
  User.email as username,
  Role.name as role
FROM domain.User
INNER JOIN domain.UserRole ON User.id = UserRole.userId
INNER JOIN domain.Role ON UserRole.roleId = Role.id
WHERE User.email = ?