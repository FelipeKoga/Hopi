//
//  DI.swift
//  iosApp
//
//  Created by Felipe Koga on 02/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import Shared

func get<A: AnyObject>() -> A {
    return KoinInit.shared.koin.get(objCClass: A.self) as! A
}

func get<A: AnyObject>(_ type: A.Type) -> A {
    return KoinInit.shared.koin.get(objCClass: A.self) as! A
}

func get<A: AnyObject>(_ type: A.Type, qualifier: (any Koin_coreQualifier)? = nil, parameter: Any) -> A {
    return KoinInit.shared.koin.get(objCClass: A.self, qualifier: qualifier, parameter: parameter) as! A
}
