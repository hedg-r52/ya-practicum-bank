{{/*
Expand the name of the chart.
*/}}
{{- define "bank-accounts.name" -}}
{{- .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "bank-accounts.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "bank-accounts.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/*
Common labels
*/}}
{{- define "bank-accounts.labels" -}}
app.kubernetes.io/name: {{ include "bank-accounts.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}
