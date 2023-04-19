apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  name: {{ .Release.Name }}-jpm-annotation-service-monitor
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: monitoring
    deployment-id:  {{ .Release.Name }}
    namespace: {{ .Release.Namespace }}
spec:
  jobLabel: {{ .Release.Name }}-jpm-annotation-service-monitor-metrics
  selector:
    matchLabels:
      product: scaiview
      component: jpm-annotation-service
      deployment-id:  {{ .Release.Name }}
  namespaceSelector:
    matchNames:
      - {{ .Release.Namespace }}
  endpoints:
    - targetPort: 8092
      path: /actuator/prometheus
---
apiVersion: v1
kind: Service
metadata:
  name: {{ .Release.Name }}-jpm-annotation-service-service
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: jpm-annotation-service
    deployment-id:  {{ .Release.Name }}
spec:
  ports:
    - port: 8092
      name: status
  selector:
    product: scaiview
    component: jpm-annotation-service
    deployment-id:  {{ .Release.Name }}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Release.Name }}-jpm-annotation-service-deployment
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: jpm-annotation-service
    deployment-id:  {{ .Release.Name }}
spec:
  selector:
    matchLabels:
      product: scaiview
      component: jpm-annotation-service
      deployment-id:  {{ .Release.Name }}
  replicas: 1
  template:
    metadata:
      labels:
        product: scaiview
        component: jpm-annotation-service
        deployment-id:  {{ .Release.Name }}
    spec:
      securityContext:
        fsGroup: 0
      containers:
      - name: container
        image: docker.arty.scai.fraunhofer.de/scaiview-jpm-annotation-service:{{ .Values.scaiview.api.version }}
        imagePullPolicy: Always
        ports:
        - containerPort: 8092
        - containerPort: 5005 # debug port
        volumeMounts:
        - mountPath: /JProMiner
          name: {{ .Release.Name }}-jpm-models-data
          subPath: JProMiner
        env:
        - name: MANAGEMENT_SERVER_PORT 
          value: "8092"
          # ARTEMIS
{{ toYaml .Values.scaiview.infra.broker | indent 8}}
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
        - name: {{ .Release.Name }}-jpm-models-data
          persistentVolumeClaim:
            claimName: {{ .Release.Name }}-jpm-models-data
