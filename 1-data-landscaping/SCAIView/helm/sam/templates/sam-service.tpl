apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: {{ .Release.Name }}-api-ingress
  annotations:
    nginx.ingress.kubernetes.io/proxy-connect-timeout: "1200"
    nginx.ingress.kubernetes.io/proxy-send-timeout: "1200"
    nginx.ingress.kubernetes.io/proxy-read-timeout: "1200"
  labels:
    component: api-service
    project: {{ .Values.PROJECT }}
    product: sam-rest-api
    version: {{ .Values.AppVersion }}
    deployment-id:  {{ .Release.Name }}
spec:
  rules:
    - host: {{ .Values.URL }}
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: {{ .Release.Name }}-api-service
                port:
                 name: "web"
---
apiVersion: v1
kind: Service
metadata:
  name: {{ .Release.Name }}-api-service
  labels:
    component: api-service
    project: {{ .Values.PROJECT }}
    product: sam-rest-api
    version: {{ .Values.AppVersion }}
    deployment-id:  {{ .Release.Name }}
spec:
  ports:
    - port: 8080
      name: web
    - port: 8092
      name: status
  selector:
    component: api-service
    version: {{ .Values.AppVersion }}
    deployment-id:  {{ .Release.Name }}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Release.Name }}-api-deployment
  labels:
    component: api-service
    project: {{ .Values.PROJECT }}
    product: sam-rest-api
    version: {{ .Values.AppVersion }}
    deployment-id:  {{ .Release.Name }}
spec:
  selector:
    matchLabels:
      component: api-service
      version: {{ .Values.AppVersion }}
      deployment-id:  {{ .Release.Name }}
  replicas: 1
  template:
    metadata:
      labels:
        component: api-service
        project: {{ .Values.PROJECT }}
        product: sam-rest-api
        version: {{ .Values.AppVersion }}
        deployment-id:  {{ .Release.Name }}
    spec:
      containers:
        - name: container
          image: {{ .Values.DOCKER_URL }}/sam-api:{{.Values.AppVersion}}
          imagePullPolicy: Always
          ports:
            - containerPort: 8081
            - containerPort: 8092
            - containerPort: 5005 # debug port
          volumeMounts:
            - mountPath: /usr/share/sam/terminologies
              name: {{ .Release.Name }}-terminology-data
              subPath: terminologies
            - mountPath: /usr/share/sam/configurations
              name: {{ .Release.Name }}-terminology-data
              subPath: configurations
          env:
            - name: MANAGEMENT_SERVER_PORT
              value: "8092"
            - name: SERVER_PORT
              value: "8080"
{{ toYaml .Values.its | indent 12}}
{{ toYaml .Values.ols | indent 12}}
{{ toYaml .Values.loinc | indent 12}}
{{ toYaml .Values.jpm | indent 12}}
          # OIDC
{{ toYaml .Values.oidc | indent 12}}
            - name: JAVA_TOOL_OPTIONS # enable debugging
              value: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
          readinessProbe:
            httpGet:
              path: /actuator/health
              port: 8092
            initialDelaySeconds: 15
            periodSeconds: 30
          livenessProbe:
            httpGet:
              path: /actuator/health
              port: 8092
            initialDelaySeconds: 60
            periodSeconds: 30
      volumes:
        - name: {{ .Release.Name }}-terminology-data
          persistentVolumeClaim:
            claimName: {{ .Release.Name }}-terminology-data
