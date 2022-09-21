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
                    echo 'Building the application.....'
                    sh 'mvn clean package'
                }
            }
        }
        stage('Build image') {
            steps {
                script {
                    echo 'Building the docker image...'
                    withCredentials([usernamePassword(credentialsId: 'nexus-id', passwordVariable: 'PASS', usernameVariable: 'USER')]) {    
                        sh "docker build -t 185.110.189.54:8084/java-maven-app:${IMAGE_NAME} ."
                    sh "echo $PASS | docker login -u $USER --password-stdin 185.110.189.54:8084"
                        sh "docker push 185.110.189.54:8084/java-maven-app:${IMAGE_NAME}"
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                echo 'Deploying....'
                }    
            }
        }
        stage ('commit version upadte') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'PASS', usernameVariable: 'USER')]) {   
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'
                        
                        sh 'git status'
                        sh 'git branch'
                        sh 'git switch incremental_versions'
                        sh 'git config --list'
                        
                        sh "git remote set-url origin https://${USER}:${PASS}@github.com/mojibakara/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "ci:version bump"'
                        sh 'git push origin HEAD:incremental_versions'
                            }
                       }        
                 }
           }   
    }
}
