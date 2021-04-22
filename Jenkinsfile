pipeline {
    agent none
    options { skipDefaultCheckout(false) }
    stages {
        stage('Build and Test') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            options { skipDefaultCheckout(false) }
            steps {
                sh 'mvn -B -DskipTests -f /var/jenkins_home/workspace/SODA/backend/springboot clean package'
            }
        }

        stage('Docker build') {
            agent any
            steps {
                sh 'docker build -t frontend:latest /var/jenkins_home/workspace/SODA/frontend/Meeting'
                sh 'docker build -t server_spring:latest /var/jenkins_home/workspace/SODA/backend/springboot'
                sh 'docker build -t server_express:latest /var/jenkins_home/workspace/SODA/backend/express'
            }
        }
        stage('Docker run') {
            agent any
            steps {
                sh 'docker ps -f name=frontend -q \
                | xargs --no-run-if-empty docker container stop'
                sh 'docker ps -f name=server_spring -q \
                | xargs --no-run-if-empty docker container stop'
                sh 'docker ps -f name=server_express -q \
                | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -f name=frontend -q \
                | xargs -r docker container rm'
                sh 'docker container ls -a -f name=server_spring -q \
                | xargs -r docker container rm'
                sh 'docker container ls -a -f name=server_express -q \
                | xargs -r docker container rm'
                sh 'docker images -f dangling=true && \
                docker rmi $(docker images -f dangling=true -q)'

                sh 'docker run -d --name frontend \
                -p 80:80 \
                -p 443:443 \
                -v
                /home/ubuntu/sslkey/:/var/jenkins_home/workspace/SODA/sslkey/ \
                --network soda_network \
                frontend:latest'
                sh 'docker run -d --name server_spring \
                --network soda_network server_spring:latest'
                sh 'docker run -d --name server_express \
                --network soda_network server_express:latest'
            }
        }
    }
}