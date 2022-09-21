#!/usr/bin/env groovy

library identifier: 'shared-library@main', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/mojibakara/shared_library.git',
         credentialsId: 'github'
        ]
)


def gv

    pipeline {
        agent any
        tools {
            maven 'maven'
        }
        stages {
            stage ("build jar") {
                steps {
                    script {
                        buildJar()
                    }
                }
            }
            stage ("build image and push image") {
                steps {
                    script {
                        buildImage '185.110.189.54:8084/java-maven-app:1.8'
                        dockerLogin()
                        dockerPush '185.110.189.54:8084/java-maven-app:1.8'
                    }
                }
            }
            stage ("deploy") {
                steps {
                    script {
                        gv.deployApp()
                    }
                }
            }
            stage ('commit version update') {
                steps {
                    script {
                        withCredentials([usernamePassword(credentialsId: 'github-id', passwordVariable: 'PASS', usernameVariable: 'USER')]) { 
                            sh 'gitt config --global user.email "jenkins@example.com'
                            sh 'git config --global user.name "jenkins"'

                            sh 'git status'
                            sh 'git branch'
                            sh 'git config --list'

                            sh "git remote set-url origin https://${USER}:${PASS}@github.com/mojibakara/java-maven-app.git"
                            sh 'git add .'
                            sh 'git commit -m "ci: version bump"'
                            sh 'git push origin HEAD:jenkins-jobs'   

                    }
                }
          }
    }
