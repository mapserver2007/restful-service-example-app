CREATE TABLE t01_userconnection(
  userId VARCHAR(255) NOT NULL,
  providerId VARCHAR(255) NOT NULL,
  providerUserId VARCHAR(255),
  rank INT NOT NULL,
  displayName VARCHAR(255),
  profileUrl VARCHAR(512),
  imageUrl VARCHAR(512),
  accessToken VARCHAR(512) NOT NULL,
  secret VARCHAR(512),
  refreshToken VARCHAR(512),
  expireTime bigint,
  PRIMARY KEY(userId, providerId, providerUserId)
);
CREATE UNIQUE INDEX IX_USER_CONNECTION ON t01_userconnection(userId, providerId, rank);