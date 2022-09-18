def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'c1a8098c-509d-4015-890b-b487ac6b5763', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t 192.168.15.134:8083/java-maven-app:1.2 .'
        sh "echo $PASS | docker login -u $USER --password-stdin 192.168.15.134:8083"
        sh 'docker push 192.168.15.134:8083/java-maven-app:1.2'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
