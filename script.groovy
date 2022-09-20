def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'nexus-id', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t 192.168.15.134:8083/java-maven-app:1.5 .'
        sh "echo $PASS | docker login -u $USER --password-stdin 192.168.15.134:8083"
        sh 'docker push 192.168.15.134:8083/java-maven-app:1.5'
    }
} 

def deployApp() {
    echo 'deploying the application.....'
} 

return this
