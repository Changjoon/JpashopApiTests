package org.example.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import java.sql.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",  // Feature 파일 경로
        glue = {"org.example.test"},  // StepDefinitions 경로 설정
        plugin = {"pretty", "html:target/cucumber-reports"},  // 보고서 생성을 위한 플러그인
        monochrome = true  // 콘솔 출력의 가독성을 높이기 위해 사용
)
public class TestRunner {

    private static Connection connection;

    @BeforeClass
    public static void setUpClass() {
        try {
            // MySQL 데이터베이스에 연결
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jpashop", "root", "1234");
            // 자동 커밋을 끄고 트랜잭션을 수동으로 관리
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // 전처리 쿼리 실행 (트랜잭션 시작)
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO Member (member_id, name, city, street, zipcode) VALUES (102, 'John', 'Seoul', 'Mapo', '12345')";
            statement.executeUpdate(sql);
//            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String selectSQL = "SELECT * FROM Member";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);

            ResultSet resultSet = selectStatement.executeQuery();

            // SELECT 결과 출력
            while (resultSet.next()) {
                String memberId = resultSet.getString("member_id");
                String name = resultSet.getString("name");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String zipcode = resultSet.getString("zipcode");

                System.out.println("MemberId: " + memberId);
                System.out.println("Name: " + name);
                System.out.println("City: " + city);
                System.out.println("Street: " + street);
                System.out.println("Zipcode: " + zipcode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            // 트랜잭션을 롤백하여 전처리 데이터를 되돌림
            connection.rollback();
//            connection.commit();
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}