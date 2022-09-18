pipeline {
    agent any    
    tools   {
        maven 'Maven'
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
                    withCredentials([usernamePassword(credentialsId: 'nexus-id', passwordVarriable: 'PASS' , usernameVarriable: 'USER')])
                    sh 'docker build -t 192.168.15.134:8083/java-maven-app:1.5 .'
                    sh "echo $PASS | docker login -u $USER --password-stdin 192.168.15.134:8083"
                    sh 'docker push 192.168.15.134:8083/java-maven-app:1.5'
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
