pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS_ID = 'docker_hub'
        DOCKERHUB_REPO           = 'blendigr/blendi_test'
        DOCKER_IMAGE_TAG         = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t %DOCKERHUB_REPO%:%DOCKER_IMAGE_TAG% ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKERHUB_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat "echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin"
                    bat "docker push %DOCKERHUB_REPO%:%DOCKER_IMAGE_TAG%"
                }
            }
        }

        stage('Docker Run') {
            steps {
                bat 'docker compose up -d'
            }
        }

        stage('Verify DB') {
            steps {
                echo 'Waiting for MariaDB to be ready...'
                bat '''
                    timeout /t 10 /nobreak
                    docker compose exec -T db mariadb -uroot -pTest12 -e "USE calc_data; SHOW TABLES; DESCRIBE calc_results;"
                '''
            }
        }
    }

    post {
        success {
            echo 'SUCCCESS!'
        }
        failure {
            echo 'FAILEDE!!!'
            bat 'docker compose down || true'
        }
    }
}