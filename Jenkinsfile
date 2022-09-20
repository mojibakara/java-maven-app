pipeline {
    agent any    
    tools   {
        maven 'maven'
    }
    stages {
        stage ("increment version") {
            steps {
               script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                       -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile ('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0] [1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
               }
                }
            }
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
                    sh "docker build -t 185.110.189.54:8084/java-maven-app:$IMAGE_NAME ."
                    sh "echo $PASS | docker login -u $USER --password-stdin 185.110.189.54:8084"
                    sh "docker push 185.110.189.54:8084/java-maven-app:$IMAGE_NAME"
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
