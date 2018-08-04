SELECT
  User.email as username,
  Role.name as role
FROM domain.User
INNER JOIN domain.UserRole ON UserRole.userId = User.id
INNER JOIN domain.Role ON Role.id = UserRole.roleId
WHERE User.email = ?