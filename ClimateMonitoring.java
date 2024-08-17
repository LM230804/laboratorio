package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClimateMonitoring {
   public void registerUser(String username, String password) {
      String query = "INSERT INTO users (username, password) VALUES (?, ?)";

      try {
         Throwable var4 = null;
         Object var5 = null;

         try {
            Connection connection = DatabaseConnection.getConnection();

            try {
               PreparedStatement preparedStatement = connection.prepareStatement(query);

               try {
                  preparedStatement.setString(1, username);
                  preparedStatement.setString(2, password);
                  preparedStatement.executeUpdate();
               } finally {
                  if (preparedStatement != null) {
                     preparedStatement.close();
                  }

               }
            } catch (Throwable var21) {
               if (var4 == null) {
                  var4 = var21;
               } else if (var4 != var21) {
                  var4.addSuppressed(var21);
               }

               if (connection != null) {
                  connection.close();
               }

               throw var4;
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Throwable var22) {
            if (var4 == null) {
               var4 = var22;
            } else if (var4 != var22) {
               var4.addSuppressed(var22);
            }

            throw var4;
         }
      } catch (SQLException var23) {
         var23.printStackTrace();
      }

   }

   public void createCenter(String centerName, String location) {
      String query = "INSERT INTO centers (name, location) VALUES (?, ?)";

      try {
         Throwable var4 = null;
         Object var5 = null;

         try {
            Connection connection = DatabaseConnection.getConnection();

            try {
               PreparedStatement preparedStatement = connection.prepareStatement(query);

               try {
                  preparedStatement.setString(1, centerName);
                  preparedStatement.setString(2, location);
                  preparedStatement.executeUpdate();
               } finally {
                  if (preparedStatement != null) {
                     preparedStatement.close();
                  }

               }
            } catch (Throwable var21) {
               if (var4 == null) {
                  var4 = var21;
               } else if (var4 != var21) {
                  var4.addSuppressed(var21);
               }

               if (connection != null) {
                  connection.close();
               }

               throw var4;
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Throwable var22) {
            if (var4 == null) {
               var4 = var22;
            } else if (var4 != var22) {
               var4.addSuppressed(var22);
            }

            throw var4;
         }
      } catch (SQLException var23) {
         var23.printStackTrace();
      }

   }

   public void insertParameters(int centerId, String parameter, String value) {
      String query = "INSERT INTO parameters (center_id, parameter, value) VALUES (?, ?, ?)";

      try {
         Throwable var5 = null;
         Object var6 = null;

         try {
            Connection connection = DatabaseConnection.getConnection();

            try {
               PreparedStatement preparedStatement = connection.prepareStatement(query);

               try {
                  preparedStatement.setInt(1, centerId);
                  preparedStatement.setString(2, parameter);
                  preparedStatement.setString(3, value);
                  preparedStatement.executeUpdate();
               } finally {
                  if (preparedStatement != null) {
                     preparedStatement.close();
                  }

               }
            } catch (Throwable var22) {
               if (var5 == null) {
                  var5 = var22;
               } else if (var5 != var22) {
                  var5.addSuppressed(var22);
               }

               if (connection != null) {
                  connection.close();
               }

               throw var5;
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Throwable var23) {
            if (var5 == null) {
               var5 = var23;
            } else if (var5 != var23) {
               var5.addSuppressed(var23);
            }

            throw var5;
         }
      } catch (SQLException var24) {
         var24.printStackTrace();
      }

   }

   public User getUserById(int userId) {
      User user = null;
      String query = "SELECT * FROM users WHERE id = ?";

      try {
         Throwable var4 = null;
         Object var5 = null;

         try {
            Connection connection = DatabaseConnection.getConnection();

            try {
               PreparedStatement preparedStatement = connection.prepareStatement(query);

               try {
                  preparedStatement.setInt(1, userId);
                  ResultSet resultSet = preparedStatement.executeQuery();
                  if (resultSet.next()) {
                     user = new User();
                     user.setId(resultSet.getInt("id"));
                     user.setUsername(resultSet.getString("username"));
                     user.setPassword(resultSet.getString("password"));
                  }
               } finally {
                  if (preparedStatement != null) {
                     preparedStatement.close();
                  }

               }
            } catch (Throwable var22) {
               if (var4 == null) {
                  var4 = var22;
               } else if (var4 != var22) {
                  var4.addSuppressed(var22);
               }

               if (connection != null) {
                  connection.close();
               }

               throw var4;
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Throwable var23) {
            if (var4 == null) {
               var4 = var23;
            } else if (var4 != var23) {
               var4.addSuppressed(var23);
            }

            throw var4;
         }
      } catch (SQLException var24) {
         var24.printStackTrace();
      }

      return user;
   }
}
