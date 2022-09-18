def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t 192.168.15.134:8083/java-maven-app:1.2 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push 192.168.15.134:8083/java-maven-app:1.2'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
