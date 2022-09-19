pipeline {
    agent any    
    tools   {
        maven 'maven'
    }
    stages {
        stage('Build jar') {
            steps {
                script  {
                    echo 'Building the application...'
                    sh 'mvn package'
                }
            }
        }
        stage('Build image') {
            steps {
                script {
                    echo 'Building the docker image...'
                    withCredentials([usernamePassword(credentialsId: 'nexus-id', passwordVariable: 'PASS', usernameVariable: 'USER')]) {    
                    sh 'docker build -t 185.110.189.54:8084/java-maven-app:1.5 .'
                    sh "echo $PASS | docker login -u $USER --password-stdin 185.110.189.54:8084"
                    sh 'docker push 185.110.189.54:8084/java-maven-app:1.5'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
