#!/usr/bin/env pwsh

# Run Maven clean install
Write-Host "Running 'mvn clean install'..."
& mvn clean install

# Function to read input with a timeout
function Read-HostWithTimeout
{
    param (
        [string]$Prompt,
        [int]$TimeoutSeconds,
        [string]$DefaultChoice
    )

    Write-Host $Prompt
    $startTime = Get-Date
    $input = ""

    while ((Get-Date) - $startTime -lt (New-TimeSpan -Seconds $TimeoutSeconds))
    {
        if ($Host.UI.RawUI.KeyAvailable)
        {
            $input = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown").Character
            break
        }
        Start-Sleep -Milliseconds 100
    }

    if ($input -eq "")
    {
        Write-Host "No input received within $TimeoutSeconds seconds. Proceeding with default option: $DefaultChoice"
        $input = $DefaultChoice
    }

    return $input
}

# Prompt user for input with a timeout
$choice = Read-HostWithTimeout "Enter your choice (1 for docker-compose up or 2 for docker-compose up --build)" 10 "1"

# Execute the chosen Docker command
switch ($choice)
{
    "1" {
        Write-Host "Running 'docker-compose up'..."
        & docker-compose up
    }
    "2" {
        Write-Host "Running 'docker-compose up --build'..."
        & docker-compose up --build
    }
    default {
        Write-Host "Invalid choice. Please run the script again and enter 1 or 2."
    }
}