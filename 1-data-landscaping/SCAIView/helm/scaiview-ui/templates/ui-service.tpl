apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: {{ .Release.Name }}-ingress
  annotations:
    nginx.ingress.kubernetes.io/proxy-connect-timeout: "300s"
    nginx.ingress.kubernetes.io/proxy-send-timeout: "300s"
    nginx.ingress.kubernetes.io/proxy-read-timeout: "300s"
  labels:
    component: scaiview-ui
    product: scaiview
    deployment-id: {{ .Release.Name }}
spec:
  rules:
  {{- range .Values.ui.domain }}
  - host: {{ . }}
    http:
      paths:
        - path: /
          pathType: Prefix
          backend:
            service:
              name: {{ $.Release.Name }}-service
              port:
                number: 80
  {{- end }}
---
apiVersion: v1
kind: Service
metadata:
  name: {{ .Release.Name }}-service
  labels:
    component: scaiview-ui
    product: scaiview
    deployment-id: {{ .Release.Name }}
spec:
  ports:
    - port: 80
      name: web
  selector:
    component: scaiview-ui
    product: scaiview
    deployment-id: {{ .Release.Name }}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Release.Name }}-deployment
  labels:
    component: scaiview-ui
    project: {{ .Values.project }}
    product: scaiview
    version: {{ .Values.ui.version }}
    deployment-id: {{ .Release.Name }}
spec:
  selector:
    matchLabels:
      component: scaiview-ui
      deployment-id: {{ .Release.Name }}
  replicas: 1
  template:
    metadata:
      labels:
        component: scaiview-ui
        product: scaiview
        deployment-id: {{ .Release.Name }}
    spec:
      containers:
        - image: {{ .Values.DOCKER_URL }}/scaiview-frontend:{{ .Values.ui.version }}
          name: container
          imagePullPolicy: Always
          ports:
          - containerPort: 80
          env:
          - name: REACT_APP_API_URL
            value: {{ .Values.ui.api.url }}
          - name: REACT_APP_OLS_URL
            value: {{ .Values.ui.ols.url }}
          - name: REACT_APP_SAM_URL
            value: {{ .Values.ui.sam.url }}
          - name: REACT_APP_LOGO_URL
            value: {{ .Values.ui.brand.logoUrl }}
          - name: REACT_APP_BRAND_NAME
            value: {{ .Values.ui.brand.name }}
          - name: REACT_APP_SHOW_WARNING
            value: {{ .Values.ui.warning.status }}
          - name: REACT_APP_SHOW_WARNING_TEXT
            value: {{ .Values.ui.warning.text }}
